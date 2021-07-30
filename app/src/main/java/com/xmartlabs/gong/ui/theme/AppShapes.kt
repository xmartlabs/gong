package com.xmartlabs.gong.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

/**
 * Created by mirland on 31/3/21.
 */
@Immutable
data class AppShapes(
    val roundedBox: CornerBasedShape = RoundedCornerShape(8.dp),
    val materialShapes: Shapes = Shapes(),
) {
    val small: CornerBasedShape
        get() = materialShapes.small
    val medium: CornerBasedShape
        get() = materialShapes.medium
    val large: CornerBasedShape
        get() = materialShapes.large

    constructor(
        roundedBox: CornerBasedShape,
        small: CornerBasedShape,
        medium: CornerBasedShape,
        large: CornerBasedShape,
    ) : this(
        roundedBox = roundedBox,
        materialShapes = Shapes(
            small = small,
            medium = medium,
            large = large,
        )
    )

    @Suppress("unused", "DataClassContainsFunctions")
    fun copy(
        roundedBox: CornerBasedShape = this.roundedBox,
        small: CornerBasedShape = this.small,
        medium: CornerBasedShape = this.medium,
        large: CornerBasedShape = this.large,
    ) = AppShapes(
        roundedBox = roundedBox,
        small = small,
        medium = medium,
        large = large,
    )
}

private val appShapes = AppShapes()

fun defaultAppShapes() = appShapes

fun appShapes() = appShapes
