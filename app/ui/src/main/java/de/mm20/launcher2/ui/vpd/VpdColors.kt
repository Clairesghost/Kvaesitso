package de.mm20.launcher2.ui.vpd

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Colour roles of the Vintage Pocket Dial design system (docs/design-system.md, "Palette roles").
 * Gradients are lists of stops, top to bottom.
 */
@Immutable
data class VpdColors(
    val isNight: Boolean,
    /** Phone body. */
    val ground: List<Color>,
    /** Dark band the app discs sit on. */
    val dialPlate: List<Color>,
    /** Ring round the clock. */
    val centreRing: List<Color>,
    val clockFace: Color,
    /** Ticks and hands of the clock. */
    val clockMarks: Color,
    /** Amber pin in the clock centre (Night only in the design, kept in both for simplicity). */
    val clockPin: Color,
    val chrome: List<Color>,
    /** Top housing; about 22% darker at night. */
    val chromeHousing: List<Color>,
    val chromeFrameLight: Color,
    val chromeFrameDark: Color,
    val disc: List<Color>,
    val discRim: Color,
    /** The three cards. */
    val card: List<Color>,
    val cardFrame: Color,
    /** Today, app list, settings, editor, pop-ups. */
    val sheet: List<Color>,
    val accent: Color,
    val alertBadge: Color,
    val alertBadgeRim: Color,
    val textOnCard: Color,
    val textOnSheet: Color,
    val textDim: Color,
    val textPlaceholder: Color,
)

private val ChromeStops = listOf(Color(0xFFFDFDFB), Color(0xFFB9BBBD), Color(0xFFE6E6E2))

private fun List<Color>.darker(factor: Float): List<Color> = map {
    Color(it.red * factor, it.green * factor, it.blue * factor, it.alpha)
}

val VpdDayColors = VpdColors(
    isNight = false,
    ground = listOf(Color(0xFFC52A35), Color(0xFFA01A24), Color(0xFF6B0C15)),
    dialPlate = listOf(Color(0xFF5A0F16), Color(0xFF2A0509)),
    centreRing = listOf(Color(0xFFC52A35), Color(0xFF8D141D)),
    clockFace = Color(0xFFFFFAF0),
    clockMarks = Color(0xFF8D141D),
    clockPin = Color(0xFF8D141D),
    chrome = ChromeStops,
    chromeHousing = ChromeStops,
    chromeFrameLight = Color(0xFFDCDEDF),
    chromeFrameDark = Color(0xFF7D7F82),
    disc = listOf(Color(0xFFFFFAF0), Color(0xFFE6D7B6)),
    discRim = Color(0xFF8D141D),
    card = listOf(Color(0xFFF2EDD2), Color(0xFFE4DCBB)),
    cardFrame = Color(0xFF000000),
    sheet = listOf(Color(0xFFFBF4E3), Color(0xFFF1E6CC)),
    accent = Color(0xFF8D141D),
    alertBadge = Color(0xFFD62B37),
    alertBadgeRim = Color(0xFFFFFAF0),
    textOnCard = Color(0xFF15110F),
    textOnSheet = Color(0xFF3A0D12),
    textDim = Color(0xFF7A4A43),
    textPlaceholder = Color(0xFF7A4A43),
)

val VpdNightColors = VpdColors(
    isNight = true,
    ground = listOf(Color(0xFF303034), Color(0xFF18181B), Color(0xFF0B0B0D)),
    dialPlate = listOf(Color(0xFF3A1219), Color(0xFF1F0B10)),
    centreRing = listOf(Color(0xFF7A1019), Color(0xFF430910)),
    clockFace = Color(0xFF17171A),
    clockMarks = Color(0xFFFFFAF0),
    clockPin = Color(0xFFFFB347),
    chrome = ChromeStops,
    chromeHousing = ChromeStops.darker(0.78f),
    chromeFrameLight = Color(0xFFDCDEDF),
    chromeFrameDark = Color(0xFF7D7F82),
    disc = listOf(Color(0xFFFFFAF0), Color(0xFFE6D7B6)),
    discRim = Color(0xFF8D141D),
    card = listOf(Color(0xFF232226), Color(0xFF141416)),
    cardFrame = Color(0xFF000000),
    sheet = listOf(Color(0xFF1C1C1F), Color(0xFF111113)),
    accent = Color(0xFFFF6A5F),
    alertBadge = Color(0xFFD62B37),
    alertBadgeRim = Color(0xFFFFFAF0),
    textOnCard = Color(0xFFF1ECE0),
    textOnSheet = Color(0xFFF1ECE0),
    textDim = Color(0xFFCFC6B4),
    textPlaceholder = Color(0xFFA8A49B),
)

/** Not provided anywhere yet; later stages provide it from the stored Look setting. */
val LocalVpdColors = staticCompositionLocalOf { VpdDayColors }
