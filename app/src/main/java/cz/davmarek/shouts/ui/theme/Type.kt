package cz.davmarek.shouts.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import cz.davmarek.shouts.R


private val satoshiFontFamily = FontFamily(
    Font(R.font.satoshi_light, FontWeight.Light),
    Font(R.font.satoshi_regular),
    Font(R.font.satoshi_medium, FontWeight.W500),
    Font(R.font.satoshi_bold, FontWeight.Bold),
    Font(R.font.satoshi_black, FontWeight.Black)

)

// Set of Material typography styles to start with
val Typography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = satoshiFontFamily),
        displayMedium = displayMedium.copy(fontFamily = satoshiFontFamily),
        displaySmall = displaySmall.copy(fontFamily = satoshiFontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = satoshiFontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = satoshiFontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = satoshiFontFamily),
        titleLarge = titleLarge.copy(fontFamily = satoshiFontFamily),
        titleMedium = titleMedium.copy(fontFamily = satoshiFontFamily),
        titleSmall = titleSmall.copy(fontFamily = satoshiFontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = satoshiFontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = satoshiFontFamily),
        bodySmall = bodySmall.copy(fontFamily = satoshiFontFamily),
        labelLarge = labelLarge.copy(fontFamily = satoshiFontFamily),
        labelMedium = labelMedium.copy(fontFamily = satoshiFontFamily),
        labelSmall = labelSmall.copy(fontFamily = satoshiFontFamily)
    )
}