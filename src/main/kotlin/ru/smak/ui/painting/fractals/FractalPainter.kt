package ru.smak.ui.painting.fractals

import org.kotlinmath.complex
import ru.smak.math.fractals.AlgebraicFractal
import ru.smak.ui.painting.CartesianPlane
import ru.smak.ui.painting.Painter
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.concurrent.Executors

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
        val threadCount = 16
        val fracService = Executors.newFixedThreadPool(threadCount)
        with(plane){
            for (i in 0 until width){
                fracService.submit {
                    val img = BufferedImage(1, height, BufferedImage.TYPE_INT_RGB)
                    for (j in 0..height) {
                        with (img.graphics)
                        {
                            color = colorFunction(fractal.isInSet(
                                complex(
                                    xScr2Crt(i),
                                    yScr2Crt(j)
                                )
                            ))
                            fillRect(0, j, 1, 1)
                        }
                    }
                    //synchronized(g){
                    g.drawImage(img, i, 0, null)
                    //}
                }
            }
        }
    }
}