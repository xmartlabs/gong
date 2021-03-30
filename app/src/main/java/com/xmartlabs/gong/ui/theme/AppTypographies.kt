@file:Suppress("unused", "MagicNumber")

package com.xmartlabs.gong.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.xmartlabs.gong.R

/**
 * Created by mirland on 26/3/21.
 */
@Immutable
data class AppTypography(
    val primaryButton: TextStyle,
    val linkButton: TextStyle,

    val materialTypography: Typography
) {
  val h1: TextStyle
    get() = materialTypography.h1

  val h2: TextStyle
    get() = materialTypography.h2

  val h3: TextStyle
    get() = materialTypography.h3

  val h4: TextStyle
    get() = materialTypography.h4

  val h5: TextStyle
    get() = materialTypography.h5

  val h6: TextStyle
    get() = materialTypography.h6

  val subtitle1: TextStyle
    get() = materialTypography.subtitle1

  val subtitle2: TextStyle
    get() = materialTypography.subtitle2

  val body1: TextStyle
    get() = materialTypography.body1

  val body2: TextStyle
    get() = materialTypography.body2

  val button: TextStyle
    get() = materialTypography.button

  val caption: TextStyle
    get() = materialTypography.caption

  val overline: TextStyle
    get() = materialTypography.overline
}

fun defaultAppTypography() = appTypography(defaultAppDims(), defaultAppColors())

fun appTypography(dims: AppDims, colors: AppColors): AppTypography {
  val baseTextStyle = TextStyle(
      fontFamily = AppFontFamilies.Roboto,
      fontWeight = FontWeight.Normal,
      fontSize = dims.textSizeRegular,
      letterSpacing = (-1.5).sp,
  )

  return AppTypography(
      primaryButton = baseTextStyle.copy(fontWeight = FontWeight.Bold),
      linkButton = baseTextStyle.copy(color = colors.linkColor),
      materialTypography = Typography(
          body1 = baseTextStyle,
          h1 = baseTextStyle.copy(fontSize = dims.textSizeH1),
          h2 = baseTextStyle.copy(fontSize = dims.textSizeH2),
          h3 = baseTextStyle.copy(fontSize = dims.textSizeH3),
          subtitle1 = TextStyle(
              fontFamily = AppFontFamilies.Roboto,
              fontWeight = FontWeight.Bold,
              color = colors.subtitleTextColor,
              fontSize = dims.textSizeMedium,
              letterSpacing = (0.11).sp,
          )
      )
  )
}

@Immutable
object AppFontFamilies {
  @Stable
  val Roboto = FontFamily(
      Font(resId = R.font.roboto_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
      Font(resId = R.font.roboto_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
      Font(resId = R.font.roboto_light, weight = FontWeight.Light, style = FontStyle.Normal),
      Font(resId = R.font.roboto_thin, weight = FontWeight.Thin, style = FontStyle.Normal),
      Font(resId = R.font.roboto_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
      Font(resId = R.font.roboto_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
      Font(resId = R.font.roboto_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
      Font(resId = R.font.roboto_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
      Font(resId = R.font.roboto_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
      Font(resId = R.font.roboto_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
  )
}
