package com.xmartlabs.gong.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

/**
 * Created by mirland on 26/3/21.
 */
@Stable
class AppColors(
    val linkTextColor: Color,
    val subtitleTextColor: Color,

    val materialColors: Colors
) {
  val primary: Color
    get() = materialColors.primary
  val primaryVariant: Color
    get() = materialColors.primaryVariant
  val secondary: Color
    get() = materialColors.secondary
  val secondaryVariant: Color
    get() = materialColors.secondaryVariant
  val background: Color
    get() = materialColors.background
  val surface: Color
    get() = materialColors.surface
  val error: Color
    get() = materialColors.error
  val onPrimary: Color
    get() = materialColors.onPrimary
  val onSecondary: Color
    get() = materialColors.onSecondary
  val onBackground: Color
    get() = materialColors.onBackground
  val onSurface: Color
    get() = materialColors.onSurface
  val onError: Color
    get() = materialColors.onError
  val isLight: Boolean
    get() = materialColors.isLight

  @Suppress("LongParameterList")
  constructor(
      linkTextColor: Color,
      subtitleTextColor: Color,
      primary: Color,
      primaryVariant: Color,
      secondary: Color,
      secondaryVariant: Color,
      background: Color,
      surface: Color,
      error: Color,
      onPrimary: Color,
      onSecondary: Color,
      onBackground: Color,
      onSurface: Color,
      onError: Color,
      isLight: Boolean
  ) : this(
      linkTextColor = linkTextColor,
      subtitleTextColor = subtitleTextColor,
      materialColors = Colors(
          primary = primary,
          primaryVariant = primaryVariant,
          secondary = secondary,
          secondaryVariant = secondaryVariant,
          background = background,
          surface = surface,
          error = error,
          onPrimary = onPrimary,
          onSecondary = onSecondary,
          onBackground = onBackground,
          onSurface = onSurface,
          onError = onError,
          isLight = isLight,
      )
  )

  @Suppress("unused", "DataClassContainsFunctions")
  fun copy(
      linkTextColor: Color = this.linkTextColor,
      subtitleTextColor: Color = this.subtitleTextColor,
      primary: Color = this.primary,
      primaryVariant: Color = this.primaryVariant,
      secondary: Color = this.secondary,
      secondaryVariant: Color = this.secondaryVariant,
      background: Color = this.background,
      surface: Color = this.surface,
      error: Color = this.error,
      onPrimary: Color = this.onPrimary,
      onSecondary: Color = this.onSecondary,
      onBackground: Color = this.onBackground,
      onSurface: Color = this.onSurface,
      onError: Color = this.onError,
      isLight: Boolean = this.isLight
  ): AppColors = AppColors(
      linkTextColor = linkTextColor,
      subtitleTextColor = subtitleTextColor,
      primary = primary,
      primaryVariant = primaryVariant,
      secondary = secondary,
      secondaryVariant = secondaryVariant,
      background = background,
      surface = surface,
      error = error,
      onPrimary = onPrimary,
      onSecondary = onSecondary,
      onBackground = onBackground,
      onSurface = onSurface,
      onError = onError,
      isLight = isLight,
  )
}

@Suppress("MagicNumber")
private object AppCustomColors {
  val WHITE_LILAC = Color(0xfff0f3fa)
  val GRAY_COD = Color(0xff121212)
  val RED_PERSIAN = Color(0xffd32f2f)
  val RED_CHESTNUT = Color(0xffcf6679)
  val WHITE = Color(0xffffffff)
  val BLUE_MIRAGE = Color(0xff1b2130)
  val BLACK = Color(0xff000000)
  val PINK_AMARANTH = Color(0xffe91e63)

  val PINK_MAUVELOUS = Color(0xfff48fb0)

  val PINK_MAROON = Color(0xffc2185b)
  val YELLOW_AMBER = Color(0xffffc107)
  val YELLOW_SALOMIE = Color(0xffffe082)

  val YELLOW_CORN = Color(0xffdda600)
  val MEDIUM_EMPHASIS_GRAY = Color(0xff808080)
}

// To expose one, but it shouldn't be necessary, the colors should be used from the app color theme
@Stable
val Color.Companion.WhiteLilac
  get() = AppCustomColors.WHITE_LILAC

@Composable
fun appColors(darkTheme: Boolean): AppColors =
    if (darkTheme) {
      darkAppColors
    } else {
      lightAppColors
    }

fun defaultAppColors() = lightAppColors

private val darkAppColors = AppColors(
    linkTextColor = AppCustomColors.PINK_AMARANTH,
    subtitleTextColor = AppCustomColors.WHITE,
    materialColors = darkColors(
        primary = AppCustomColors.PINK_MAUVELOUS,
        primaryVariant = AppCustomColors.PINK_MAROON,
        secondary = AppCustomColors.YELLOW_SALOMIE,
        secondaryVariant = AppCustomColors.YELLOW_SALOMIE,
        background = AppCustomColors.GRAY_COD,
        surface = AppCustomColors.GRAY_COD,
        onPrimary = AppCustomColors.BLACK,
        onSecondary = AppCustomColors.BLACK,
        onBackground = AppCustomColors.WHITE,
        onSurface = AppCustomColors.WHITE,
        error = AppCustomColors.RED_CHESTNUT,
        onError = AppCustomColors.BLACK,
    )
)

private val lightAppColors = AppColors(
    linkTextColor = AppCustomColors.YELLOW_AMBER,
    subtitleTextColor = AppCustomColors.MEDIUM_EMPHASIS_GRAY,
    materialColors = lightColors(
        primary = AppCustomColors.PINK_AMARANTH,
        primaryVariant = AppCustomColors.PINK_MAROON,
        secondary = AppCustomColors.YELLOW_AMBER,
        secondaryVariant = AppCustomColors.YELLOW_CORN,
        background = AppCustomColors.WHITE_LILAC,
        surface = AppCustomColors.WHITE,
        onPrimary = AppCustomColors.WHITE,
        onSecondary = AppCustomColors.WHITE,
        onBackground = AppCustomColors.BLUE_MIRAGE,
        onSurface = AppCustomColors.BLACK,
        error = AppCustomColors.RED_PERSIAN,
        onError = AppCustomColors.WHITE,
    )
)
