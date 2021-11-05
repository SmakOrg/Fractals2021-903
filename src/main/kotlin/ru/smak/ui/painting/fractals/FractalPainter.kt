package ru.smak.ui.painting.fractals

import org.kotlinmath.complex
import ru.smak.math.fractals.AlgebraicFractal
import ru.smak.ui.painting.CartesianPlane
import ru.smak.ui.painting.Painter
import java.awt.Color
import java.awt.Graphics

class FractalPainter(
    var fractal: AlgebraicFractal,
    var plane: CartesianPlane,
    var colorFunction: (Float)->Color
) : Painter {

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