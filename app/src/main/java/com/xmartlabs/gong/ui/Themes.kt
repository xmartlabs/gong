package com.xmartlabs.gong.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xmartlabs.gong.R

object Themes {

  @Composable
  fun GongTheme(
      darkTheme: Boolean = isSystemInDarkTheme(),
      content: @Composable () -> Unit,
  ) {
    MaterialTheme(
        colors = if (darkTheme) GongColors.darkThemeColors else GongColors.lightThemeColors,
        typography = Typographies.gongTypography,
        content = content,
    )
  }
}

object GongColors {
  val background = Color(0xfff0f3fa)
  val backgroundDark = Color(0xff121212)
  val error = Color(0xffd32f2f)
  val errorDark = Color(0xffcf6679)
  val onBackgroundDark = Color(0xffffffff)
  val onBackgroundLight = Color(0xff1b2130)
  val onError = Color(0xffffffff)
  val onErrorDark = Color(0xff000000)
  val onPrimary = Color(0xffffffff)
  val onPrimaryDark = Color(0xff000000)
  val onSecondary = Color(0xffffffff)
  val onSecondaryDark = Color(0xff000000)
  val onSurface = Color(0xff000000)
  val onSurfaceDark = Color(0xffffffff)
  val primary = Color(0xffe91e63)
  val primaryDark = Color(0xfff48fb0)
  val primaryVariant = Color(0xffc2185b)
  val primaryVariantDark = Color(0xffc2185b)
  val secondary = Color(0xffffc107)
  val secondaryDark = Color(0xffffe082)
  val secondaryVariant = Color(0xffdda600)
  val secondaryVariantDark = Color(0xffffe082)
  val surface = Color(0xffffffff)
  val surfaceDark = Color(0xff121212)

  val mediumEmphasisGray = Color(0xff808080)
  val inputGray = Color(0xfff8f9fa)
  val transparent = Color(0x00000000)

  val lightThemeColors = lightColors(
      primary = primary,
      primaryVariant = primaryVariant,
      secondary = secondary,
      secondaryVariant = secondaryVariant,
      background = background,
      surface = surface,
      onPrimary = onPrimary,
      onSecondary = onSecondary,
      onBackground = onBackgroundLight,
      onSurface = onSurface,
      error = error,
      onError = onError,
  )

  val darkThemeColors = darkColors(
      primary = primaryDark,
      primaryVariant = primaryVariantDark,
      secondary = secondaryDark,
      secondaryVariant = secondaryVariantDark,
      background = backgroundDark,
      surface = surfaceDark,
      onPrimary = onPrimaryDark,
      onSecondary = onSecondaryDark,
      onBackground = onBackgroundDark,
      onSurface = onSurfaceDark,
      error = errorDark,
      onError = onErrorDark,
  )
}

object Typographies {
  private val robotoFamily = FontFamily(
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

  val h1TextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Normal,
      fontSize = 96.sp,
      letterSpacing = (-1.5).sp,
  )
  val h2TextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Light,
      fontSize = 60.sp,
      letterSpacing = (-0.5).sp,
  )
  val h3TextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Normal,
      fontSize = 40.sp,
      letterSpacing = 0.sp,
  )
  val h4TextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Normal,
      fontSize = 34.sp,
      letterSpacing = (0.25).sp,
  )
  val h5TextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Normal,
      fontSize = 24.sp,
      letterSpacing = 0.sp,
  )
  val h6TextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Medium,
      fontSize = 20.sp,
      letterSpacing = (0.15).sp,
  )
  val subtitle1TextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Normal,
      fontSize = 17.sp,
      letterSpacing = (0.16).sp,
  )
  val subtitle2TextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp,
      letterSpacing = (0.11).sp,
  )
  val body1TextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Normal,
      fontSize = 17.sp,
      letterSpacing = (0.47).sp,
  )
  val body2TextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Normal,
      fontSize = 15.sp,
      letterSpacing = (0.27).sp,
  )
  val buttonTextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Bold,
      fontSize = 14.sp,
      letterSpacing = (1.35).sp,
  )
  val captionTextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Normal,
      fontSize = 13.sp,
      letterSpacing = (0.43).sp,
  )
  val overlineTextStyle = TextStyle(
      fontFamily = robotoFamily,
      fontWeight = FontWeight.Bold,
      fontSize = 10.sp,
      letterSpacing = (1.5).sp,
  )

  val gongTypography = Typography(
      h1 = h1TextStyle,
      h2 = h2TextStyle,
      h3 = h3TextStyle,
      h4 = h4TextStyle,
      h5 = h5TextStyle,
      h6 = h6TextStyle,
      subtitle1 = subtitle1TextStyle,
      subtitle2 = subtitle2TextStyle,
      body1 = body1TextStyle,
      body2 = body2TextStyle,
      button = buttonTextStyle,
      caption = captionTextStyle,
      overline = overlineTextStyle,
  )
}

object Shapes {
  val roundedBox = RoundedCornerShape(8.dp)
}
