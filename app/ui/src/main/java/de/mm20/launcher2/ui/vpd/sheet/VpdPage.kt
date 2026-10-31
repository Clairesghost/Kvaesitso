package de.mm20.launcher2.ui.vpd.sheet

import de.mm20.launcher2.search.SavableSearchable

/** Pages that open as a sheet over Home. The last one in the stack is on screen. */
sealed interface VpdPage {
    data object Apps : VpdPage
    data object Settings : VpdPage

    /** Dial editor; [place] is an app the owner chose with "Put on the dial" and still has to drop on a disc. */
    data class Editor(val place: SavableSearchable? = null) : VpdPage
}
