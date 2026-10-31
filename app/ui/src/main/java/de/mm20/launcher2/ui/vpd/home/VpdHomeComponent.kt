package de.mm20.launcher2.ui.vpd.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import de.mm20.launcher2.ui.launcher.scaffold.LauncherScaffoldState
import de.mm20.launcher2.ui.launcher.scaffold.components.ScaffoldComponent

/**
 * Home component of Vintage Pocket Dial (replaces ClockHomeComponent, AF-22). It draws its own
 * full-screen body, so the scaffold's insets are not applied; the screen reads the bars itself (AF-13).
 */
internal object VpdHomeComponent : ScaffoldComponent() {

    override val drawBackground: Boolean = false

    override var isAtTop: State<Boolean?> = mutableStateOf(true)
    override var isAtBottom: State<Boolean?> = mutableStateOf(true)

    @Composable
    override fun Component(
        modifier: Modifier,
        insets: PaddingValues,
        state: LauncherScaffoldState,
    ) {
        VpdHomeScreen(state = state, modifier = modifier)
    }
}
