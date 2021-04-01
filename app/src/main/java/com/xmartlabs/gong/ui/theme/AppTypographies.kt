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

  constructor(
      primaryButton: TextStyle,
      linkButton: TextStyle,
      h1: TextStyle,
      h2: TextStyle,
      h3: TextStyle,
      h4: TextStyle,
      h5: TextStyle,
      h6: TextStyle,
      subtitle1: TextStyle,
      subtitle2: TextStyle,
      body1: TextStyle,
      body2: TextStyle,
      button: TextStyle,
      caption: TextStyle,
      overline: TextStyle,
  ) : this(
      primaryButton = primaryButton,
      linkButton = linkButton,
      materialTypography = Typography(
          h1 = h1,
          h2 = h2,
          h3 = h3,
          h4 = h4,
          h5 = h5,
          h6 = h6,
          subtitle1 = subtitle1,
          subtitle2 = subtitle2,
          body1 = body1,
          body2 = body2,
          button = button,
          caption = caption,
          overline = overline,
      )
  )

  @Suppress("unused", "DataClassContainsFunctions")
  fun copy(
      primaryButton: TextStyle = this.primaryButton,
      linkButton: TextStyle = this.linkButton,
      h1: TextStyle = this.h1,
      h2: TextStyle = this.h2,
      h3: TextStyle = this.h3,
      h4: TextStyle = this.h4,
      h5: TextStyle = this.h5,
      h6: TextStyle = this.h6,
      subtitle1: TextStyle = this.subtitle1,
      subtitle2: TextStyle = this.subtitle2,
      body1: TextStyle = this.body1,
      body2: TextStyle = this.body2,
      button: TextStyle = this.button,
      caption: TextStyle = this.caption,
      overline: TextStyle = this.overline,
  ) = AppTypography(
      primaryButton = primaryButton,
      linkButton = linkButton,
      h1 = h1,
      h2 = h2,
      h3 = h3,
      h4 = h4,
      h5 = h5,
      h6 = h6,
      subtitle1 = subtitle1,
      subtitle2 = subtitle2,
      body1 = body1,
      body2 = body2,
      button = button,
      caption = caption,
      overline = overline,
  )
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
      linkButton = baseTextStyle.copy(color = colors.linkTextColor),
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
