package com.xmartlabs.gong.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

object AppTheme {
  val dims: AppDims
    @Composable
    @ReadOnlyComposable
    get() = LocalAppDims.current

  val colors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current

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

private val LocalAppTypography = staticCompositionLocalOf {
  defaultAppTypography()
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
  val appColors = appColors(darkTheme = darkTheme)
  val appDims = appDims()
  val appTypography = appTypography(appDims, appColors)
  CompositionLocalProvider(
      LocalAppDims provides appDims,
      LocalAppColors provides appColors,
      LocalAppTypography provides appTypography,
  ) {
    MaterialTheme(
        colors = appColors.materialColors,
        typography = appTypography.materialTypography,
        content = content,
    )
  }
}

object Shapes {
  val roundedBox = RoundedCornerShape(8.dp)
}
