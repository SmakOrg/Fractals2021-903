package ru.smak.ui.painting.fractals

import org.kotlinmath.complex
import ru.smak.math.fractals.AlgebraicFractal
import ru.smak.ui.painting.CartesianPlane
import ru.smak.ui.painting.Painter
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.concurrent.Callable
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

    private val threadCount = 16
    private var fracService = Executors.newFixedThreadPool(threadCount)

    override fun paint(g: Graphics) {
        val t1 = System.currentTimeMillis()
        with(plane){
            if (width <= 0 || height <= 0) return
            if (!(fracService.isShutdown || fracService.isTerminated)) {
                fracService.shutdown()
                fracService = Executors.newFixedThreadPool(threadCount)
            }
            val subWidth = 10
            for (i in 0 until (width / subWidth)){
                q.put(
                    Pair(
                        i * subWidth,
                        fracService.submit(Callable {
                            val wsz = subWidth + if (i+1 == width/subWidth) width % subWidth else 0
                            val img = BufferedImage(wsz, height, BufferedImage.TYPE_INT_RGB)
                            for (k in 0 until wsz) {
                                for (j in 0..height) {
                                    with (img.graphics) {
                                        color = colorFunction(
                                            fractal.isInSet(
                                                complex(
                                                    xScr2Crt(i*wsz+k),
                                                    yScr2Crt(j)
                                                )
                                            )
                                        )
                                        fillRect(k, j, 1, 1)
                                    }
                                }
                            }
                            img
                        })
                    )
                )
            }
            try {
                for (i in 0 until (width / subWidth)) {
                    q.take().run {
                        g.drawImage(second.get(), first, 0, null)
                    }
                }
            } catch (ex: InterruptedException){
            }
        }
        val t2 = System.currentTimeMillis()
        println(t2 - t1)
    }
}