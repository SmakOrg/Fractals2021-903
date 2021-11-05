package ru.smak.ui.painting.fractals

import java.awt.Color

fun pinkFractal(x: Float): Color {
    if (x == 1F) return Color.BLACK
    return Color(
        Math.abs(Math.cos(Math.log(12.0 * (1.0 - x)))).toFloat(),
        Math.abs(Math.sin(6.0 * (1.0 - x))).toFloat(),
        Math.abs(Math.sin(7.0 - 7.0 * x) * Math.cos(13.0 * x)).toFloat()
    )
}

fun grayFractal(x: Float) = Color(1-x, 1-x, 1-x)