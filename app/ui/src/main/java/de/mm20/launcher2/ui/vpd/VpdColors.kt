package de.mm20.launcher2.ui.vpd

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import de.mm20.launcher2.preferences.ui.VpdColour

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

/** Per-colour values from the mock-up CSS (`.A.c-*`, D-177). Cherry is the default token set above. */
private class VpdColourSet(
    val accent: Color,
    val accentNight: Color,
    val ground: List<Color>,
    val groundNight: List<Color>,
    val plate: List<Color>,
    val plateNight: List<Color>,
    val ring: List<Color>,
    val ringNight: List<Color>,
)

private fun c(hex: Long) = Color(0xFF000000 or hex)

private val MintSet = VpdColourSet(
    accent = c(0x1F7A5A), accentNight = c(0x7FE0B8),
    ground = listOf(c(0x9BDCBF), c(0x62B895), c(0x2F7A5E)),
    groundNight = listOf(c(0x2A3A33), c(0x141C18), c(0x080C0A)),
    plate = listOf(c(0x1D4A3A), c(0x0D2820)), plateNight = listOf(c(0x16382C), c(0x0B2019)),
    ring = listOf(c(0x6FC5A1), c(0x2F8A68)), ringNight = listOf(c(0x1F5A44), c(0x0F3326)),
)

private val MidnightSet = VpdColourSet(
    accent = c(0x2A4D94), accentNight = c(0xFFB347),
    ground = listOf(c(0x4A6DBA), c(0x2A4D94), c(0x15295A)),
    groundNight = listOf(c(0x232A40), c(0x10141F), c(0x07090F)),
    plate = listOf(c(0x0E1C3D), c(0x060D1F)), plateNight = listOf(c(0x131F3F), c(0x0A1228)),
    ring = listOf(c(0x5A7FD0), c(0x24407A)), ringNight = listOf(c(0x1D2F5E), c(0x0F1A38)),
)

private val PowderSet = VpdColourSet(
    accent = c(0x2F6FA0), accentNight = c(0x8CC8F0),
    ground = listOf(c(0xB6DCF2), c(0x86BDE0), c(0x4F8FB8)),
    groundNight = listOf(c(0x2A3540), c(0x141B22), c(0x080B0F)),
    plate = listOf(c(0x16405E), c(0x0A2236)), plateNight = listOf(c(0x12293C), c(0x091722)),
    ring = listOf(c(0x8CC6EA), c(0x3F86B5)), ringNight = listOf(c(0x1D4260), c(0x0F2638)),
)

/**
 * Colour tokens for a look (Day / Night) and a colour variation. Chrome, ivory discs and cards
 * stay as they are; body, clock ring, dial plate and accents change (D-177).
 */
fun vpdColors(night: Boolean, colour: VpdColour): VpdColors {
    val base = if (night) VpdNightColors else VpdDayColors
    val set = when (colour) {
        VpdColour.Cherry -> return base
        VpdColour.Mint -> MintSet
        VpdColour.Midnight -> MidnightSet
        VpdColour.Powder -> PowderSet
    }
    return base.copy(
        ground = if (night) set.groundNight else set.ground,
        dialPlate = if (night) set.plateNight else set.plate,
        centreRing = if (night) set.ringNight else set.ring,
        clockMarks = if (night) base.clockMarks else set.accent,
        clockPin = if (night) base.clockPin else set.accent,
        discRim = set.accent,
        accent = if (night) set.accentNight else set.accent,
    )
}
