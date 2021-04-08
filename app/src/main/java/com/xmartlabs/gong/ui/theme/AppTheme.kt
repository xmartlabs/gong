package com.xmartlabs.gong.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

object AppTheme {
  val dims: AppDims
    @Composable
    @ReadOnlyComposable
    get() = LocalAppDims.current

  val colors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current

  val shapes: AppShapes
    @Composable
    @ReadOnlyComposable
    get() = LocalAppShapes.current

  val typography: AppTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalAppTypography.current
}

private val LocalAppColors = staticCompositionLocalOf {
  defaultAppColors()
}

private val LocalAppDims = staticCompositionLocalOf {
  defaultAppDims()
}

private val LocalAppShapes = staticCompositionLocalOf {
  defaultAppShapes()
}

private val LocalAppTypography = staticCompositionLocalOf {
  defaultAppTypography()
}

@Composable
fun AppTheme(
    dims: AppDims = appDims(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorPalette: AppColorPalette = AppColorPalette.PINK,
    typography: AppTypography = appTypography(dims, appColors(colorPalette = colorPalette, darkTheme = darkTheme)),
    shapes: AppShapes = appShapes(),
    content: @Composable () -> Unit,
) = AppTheme(
    dims = dims,
    colors = appColors(colorPalette = colorPalette, darkTheme = darkTheme),
    typography = typography,
    shapes = shapes,
    content = content
)

@Composable
fun AppTheme(
    dims: AppDims = appDims(),
    colors: AppColors = appColors(colorPalette = AppColorPalette.PINK, darkTheme = isSystemInDarkTheme()),
    typography: AppTypography = appTypography(dims, colors),
    shapes: AppShapes = appShapes(),
    content: @Composable () -> Unit,
) {
  CompositionLocalProvider(
      LocalAppColors provides colors,
      LocalAppDims provides dims,
      LocalAppShapes provides shapes,
      LocalAppTypography provides typography,
  ) {
    MaterialTheme(
        colors = colors.materialColors,
        typography = typography.materialTypography,
        shapes = shapes.materialShapes,
        content = content,
    )
  }
}
