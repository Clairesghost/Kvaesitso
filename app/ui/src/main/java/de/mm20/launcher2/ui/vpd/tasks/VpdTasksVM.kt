package de.mm20.launcher2.ui.vpd.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

/** Feeds the Tasks card. All file work runs on [Dispatchers.IO]. */
class VpdTasksVM(application: Application) : AndroidViewModel(application) {
    private val store = VpdTasksStore(File(application.filesDir, "vpd-tasks.json"))

    val tasks: StateFlow<List<VpdTask>> = store.tasks

    init {
        viewModelScope.launch(Dispatchers.IO) { store.load() }
    }

    fun add(text: String) {
        val clean = text.trim().take(MAX_LENGTH)
        if (clean.isEmpty()) return
        viewModelScope.launch(Dispatchers.IO) { store.add(clean) }
    }

    fun toggle(id: Long) {
        viewModelScope.launch(Dispatchers.IO) { store.toggle(id) }
    }

    /** Called when Home resumes (D-192). */
    fun removeDone() {
        viewModelScope.launch(Dispatchers.IO) { store.removeDone() }
    }

    private companion object {
        const val MAX_LENGTH = 200
    }
}
