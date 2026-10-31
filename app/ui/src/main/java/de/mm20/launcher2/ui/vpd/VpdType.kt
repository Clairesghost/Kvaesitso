package de.mm20.launcher2.ui.vpd

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import de.mm20.launcher2.ui.R

/**
 * Fonts of the Vintage Pocket Dial design system (docs/design-system.md, "Fonts").
 * Both are bundled in res/font (SIL OFL, licence texts in docs/licenses/fonts).
 */
object VpdFonts {
    /** Headings: Oleo Script Swash Caps 700. Big words only. */
    val Heading = FontFamily(
        Font(R.font.oleo_script_swash_caps_bold, FontWeight.Bold)
    )

    /** Everything else: Libre Franklin 400, 500, 600, 700 (one variable font file). */
    val Body = FontFamily(
        libreFranklin(FontWeight.Normal),
        libreFranklin(FontWeight.Medium),
        libreFranklin(FontWeight.SemiBold),
        libreFranklin(FontWeight.Bold),
    )

    private fun libreFranklin(weight: FontWeight) = Font(
        resId = R.font.libre_franklin,
        weight = weight,
        variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight)),
    )
}

/** The two type styles: script headings and Libre Franklin body. */
object VpdTextStyles {
    val Heading = TextStyle(
        fontFamily = VpdFonts.Heading,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
    )

    /** Script, but never below 15 sp. */
    val HeadingSmall = TextStyle(
        fontFamily = VpdFonts.Heading,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
    )

    val Body = TextStyle(
        fontFamily = VpdFonts.Body,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    )

    /** Sender names (D-118). */
    val BodyMedium = Body.copy(fontWeight = FontWeight.Medium)
    val BodySemiBold = Body.copy(fontWeight = FontWeight.SemiBold)
    val BodyBold = Body.copy(fontWeight = FontWeight.Bold)
}
