package com.example.currencyconverterapp.watchlist.presentation.util

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

enum class TriangleType {
    TOP_LEFT,
    BOTTOM_RIGHT
}

fun constructTrianglePath(size: Size, triangleType: TriangleType): Path {
    return Path().apply {
        if (triangleType == TriangleType.TOP_LEFT) {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(0f, size.height)
        } else if (triangleType == TriangleType.BOTTOM_RIGHT) {
            moveTo(size.width, size.height)
            lineTo(size.width, 0f)
            lineTo(0f, size.height)
        }
        close()
    }
}

class TriangleShape(private val triangleType: TriangleType): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(constructTrianglePath(size, triangleType))
    }
}
