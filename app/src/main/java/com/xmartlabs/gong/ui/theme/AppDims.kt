package com.xmartlabs.gong.ui.theme

import androidx.annotation.Dimension
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by mirland on 26/3/21.
 */
@Dimension(unit = Dimension.DP)
private const val SMALL_DEVICE_SCREEN_WIDTH_DP = 300

@Stable
class AppDims(
    val textSizeSmall: TextUnit,
    val textSizeMedium: TextUnit,
    val textSizeRegular: TextUnit,
    val textSizeExtraBig: TextUnit,

    val textSizeH1: TextUnit,
    val textSizeH2: TextUnit,
    val textSizeH3: TextUnit,

    val buttonRadiusRegular: Dp,
    val buttonRadiusBig: Dp,

    val listItemStartPadding: Dp,
    val listItemEndPadding: Dp,
)

@Composable
fun appDims() = if (LocalConfiguration.current.screenWidthDp < SMALL_DEVICE_SCREEN_WIDTH_DP) {
  smallDeviceAppDims
} else {
  regularAppDims
}

fun defaultAppDims() = regularAppDims

private val regularAppDims = AppDims(
    textSizeSmall = 12.sp,
    textSizeMedium = 14.sp,
    textSizeRegular = 16.sp,
    textSizeExtraBig = 42.sp,

    textSizeH1 = 96.sp,
    textSizeH2 = 60.sp,
    textSizeH3 = 48.sp,

    buttonRadiusRegular = 4.dp,
    buttonRadiusBig = 8.dp,
    listItemStartPadding = 27.dp,
    listItemEndPadding = 30.dp,
)

private val smallDeviceAppDims = AppDims(
    textSizeSmall = 12.sp,
    textSizeMedium = 13.sp,
    textSizeRegular = 14.sp,
    textSizeExtraBig = 26.sp,

    textSizeH1 = 60.sp,
    textSizeH2 = 48.sp,
    textSizeH3 = 32.sp,

    buttonRadiusRegular = 4.dp,
    buttonRadiusBig = 8.dp,
    listItemStartPadding = 15.dp,
    listItemEndPadding = 14.dp,
)
