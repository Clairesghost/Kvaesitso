package de.mm20.launcher2.preferences.ui

import de.mm20.launcher2.preferences.LauncherDataStore
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

/**
 * Vintage Pocket Dial: which look the launcher uses. Auto follows the phone's dark mode.
 */
@Serializable
enum class VpdLook {
    Auto,
    Day,
    Night,
}

class VpdLookSettings internal constructor(
    private val launcherDataStore: LauncherDataStore,
) {
    val look
        get() = launcherDataStore.data.map { it.vpdLook }.distinctUntilChanged()

    fun setLook(look: VpdLook) {
        launcherDataStore.update {
            it.copy(vpdLook = look)
        }
    }
}
