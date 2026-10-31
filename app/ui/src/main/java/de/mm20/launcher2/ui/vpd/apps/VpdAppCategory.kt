package de.mm20.launcher2.ui.vpd.apps

import android.content.pm.ApplicationInfo

/** Categories of the app list (D-168). Android's own app category decides first; no category = Other. */
enum class VpdAppCategory(val label: String) {
    Games("Games"),
    Audio("Audio"),
    Video("Video"),
    Photos("Photos"),
    Social("Social"),
    News("News"),
    Maps("Maps"),
    Productivity("Productivity"),
    Accessibility("Accessibility"),
    Other("Other");

    companion object {
        fun fromAndroid(category: Int): VpdAppCategory = when (category) {
            ApplicationInfo.CATEGORY_GAME -> Games
            ApplicationInfo.CATEGORY_AUDIO -> Audio
            ApplicationInfo.CATEGORY_VIDEO -> Video
            ApplicationInfo.CATEGORY_IMAGE -> Photos
            ApplicationInfo.CATEGORY_SOCIAL -> Social
            ApplicationInfo.CATEGORY_NEWS -> News
            ApplicationInfo.CATEGORY_MAPS -> Maps
            ApplicationInfo.CATEGORY_PRODUCTIVITY -> Productivity
            ApplicationInfo.CATEGORY_ACCESSIBILITY -> Accessibility
            else -> Other
        }

        fun fromName(name: String?): VpdAppCategory? = entries.firstOrNull { it.name == name }
    }
}
