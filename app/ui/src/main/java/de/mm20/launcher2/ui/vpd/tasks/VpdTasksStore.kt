package de.mm20.launcher2.ui.vpd.tasks

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.File

/** One task kept inside the launcher (D-190). Time is milliseconds since 1970. */
@Serializable
data class VpdTask(
    val id: Long,
    val text: String,
    val created: Long,
    val done: Boolean = false,
)

/**
 * Tasks as one small JSON file in the app's files folder. Blocking file work: call from
 * a background dispatcher only. All changes go through [mutex], newest task first.
 * Failures are logged with the tag "launcher" (class name only, never task text).
 */
class VpdTasksStore(private val file: File) {
    private val mutex = Mutex()
    private val json = Json { ignoreUnknownKeys = true }
    private val serializer = ListSerializer(VpdTask.serializer())
    private val _tasks = MutableStateFlow<List<VpdTask>>(emptyList())
    private var loaded = false

    val tasks: StateFlow<List<VpdTask>> = _tasks

    suspend fun load() = mutex.withLock { ensureLoaded() }

    suspend fun add(text: String) = mutex.withLock {
        ensureLoaded()
        val now = System.currentTimeMillis()
        val id = (_tasks.value.maxOfOrNull { it.id } ?: 0L) + 1L
        update(listOf(VpdTask(id = id, text = text, created = now)) + _tasks.value)
    }

    suspend fun toggle(id: Long) = mutex.withLock {
        ensureLoaded()
        update(_tasks.value.map { if (it.id == id) it.copy(done = !it.done) else it })
    }

    /** Removes every crossed-out task. */
    suspend fun removeDone() = mutex.withLock {
        ensureLoaded()
        if (_tasks.value.any { it.done }) update(_tasks.value.filter { !it.done })
    }

    private fun ensureLoaded() {
        if (loaded) return
        loaded = true
        if (!file.exists()) return
        try {
            _tasks.value = json.decodeFromString(serializer, file.readText())
        } catch (e: Exception) {
            Log.e("launcher", "tasks read failed: ${e.javaClass.simpleName}")
            // keep the unreadable file for later, so the next write does not destroy it
            try {
                file.renameTo(File(file.parentFile, file.name + ".bad"))
            } catch (e2: Exception) {
                Log.e("launcher", "tasks rename failed: ${e2.javaClass.simpleName}")
            }
        }
    }

    private fun update(list: List<VpdTask>) {
        _tasks.value = list
        try {
            val tmp = File(file.parentFile, file.name + ".tmp")
            tmp.writeText(json.encodeToString(serializer, list))
            if (!tmp.renameTo(file)) {
                Log.e("launcher", "tasks write failed: rename")
            }
        } catch (e: Exception) {
            Log.e("launcher", "tasks write failed: ${e.javaClass.simpleName}")
        }
    }
}
