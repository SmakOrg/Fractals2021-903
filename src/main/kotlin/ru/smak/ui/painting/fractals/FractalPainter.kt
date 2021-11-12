package ru.smak.ui.painting.fractals

import org.kotlinmath.complex
import ru.smak.math.fractals.AlgebraicFractal
import ru.smak.ui.painting.CartesianPlane
import ru.smak.ui.painting.Painter
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics

class FractalPainter(
    val fractal: AlgebraicFractal,
    val plane: CartesianPlane,
    var colorFunction: (Double)->Color
) : Painter {

    override var size: Dimension
        get() = plane.pixelSize
        set(value) {
            plane.pixelSize = value
        }

    override fun paint(g: Graphics) {
        with(g){
            with(plane){
                for (i in 0..width){
                    for (j in 0..height){
                        color = colorFunction(fractal.isInSet(complex(
                            xScr2Crt(i),
                            yScr2Crt(j)
                        )))
                        fillRect(i, j, 1, 1)
                    }
                }
            }
        }
    }
}