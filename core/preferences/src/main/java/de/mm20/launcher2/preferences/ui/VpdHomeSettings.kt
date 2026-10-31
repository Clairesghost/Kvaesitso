package de.mm20.launcher2.preferences.ui

import de.mm20.launcher2.preferences.LauncherDataStore
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

/**
 * Vintage Pocket Dial: how much the launcher moves. Off = no spin animation and no press animation.
 */
@Serializable
enum class VpdMotion {
    Full,
    Minimal,
    Off,
}

class VpdHomeSettings internal constructor(
    private val launcherDataStore: LauncherDataStore,
) {
    val motion
        get() = launcherDataStore.data.map { it.vpdMotion }.distinctUntilChanged()

    fun setMotion(motion: VpdMotion) {
        launcherDataStore.update {
            it.copy(vpdMotion = motion)
        }
    }

    /** The dial ring returns to its home position 5 s after it stops (D-126). */
    val ringReturnsHome
        get() = launcherDataStore.data.map { it.vpdRingReturnsHome }.distinctUntilChanged()

    fun setRingReturnsHome(returns: Boolean) {
        launcherDataStore.update {
            it.copy(vpdRingReturnsHome = returns)
        }
    }
}
