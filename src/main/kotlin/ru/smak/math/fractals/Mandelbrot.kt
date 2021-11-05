package ru.smak.math.fractals

import org.kotlinmath.Complex
import org.kotlinmath.ZERO
import ru.smak.math.mod2

object Mandelbrot : AlgebraicFractal{

    var maxIterations = 200
    private var R2: Double = 4.0
    var R: Double = 2.0
        set(value) {
            field = value
            R2 = value * value
        }

    override fun isInSet(c: Complex): Float {
        var z = ZERO
        for(i in 0 until maxIterations){
            z = z * z + c
            if (z.mod2 > R2) return i.toFloat() / maxIterations
        }
        return 1F
    }
}