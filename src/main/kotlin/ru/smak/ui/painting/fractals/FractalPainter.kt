package ru.smak.ui.painting.fractals

import org.kotlinmath.complex
import ru.smak.math.fractals.AlgebraicFractal
import ru.smak.ui.painting.CartesianPlane
import ru.smak.ui.painting.Painter
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import kotlin.concurrent.thread

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
        with(plane){
            List<Thread>(threadCount) { tNum ->
                thread {
                    val w = width / threadCount
                    val start = tNum * w
                    val end   = (tNum + 1) * w - 1 + if (tNum == threadCount-1) width % threadCount else 0
                    val img = BufferedImage(end-start+1, height, BufferedImage.TYPE_INT_RGB)
                    for (i in start..end) {
                        for (j in 0..height) {
                            with (img.graphics)
                            {
                                color = colorFunction(fractal.isInSet(
                                    complex(
                                        xScr2Crt(i),
                                        yScr2Crt(j)
                                    )
                                ))
                                fillRect(i - start, j, 1, 1)
                            }
                        }
                    }
                    synchronized(g){
                        g.drawImage(img, start, 0, null)
                    }
                }
            }.forEach {
                it.join()
            }
        }
    }
}