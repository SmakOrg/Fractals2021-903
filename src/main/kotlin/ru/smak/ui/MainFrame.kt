package ru.smak.ui

import ru.smak.math.fractals.Mandelbrot
import ru.smak.ui.painting.CartesianPlane
import ru.smak.ui.painting.SelectablePanel
import ru.smak.ui.painting.fractals.FractalPainter
import ru.smak.ui.painting.fractals.colorizers
import java.awt.Color
import java.awt.Dimension
import javax.swing.GroupLayout
import javax.swing.JFrame
import kotlin.random.Random

class MainFrame : JFrame() {

    val fractalPanel: SelectablePanel

    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(600, 400)
        val painter = FractalPainter(
            Mandelbrot(),
            CartesianPlane(-2.0, 1.0, -1.0, 1.0),
            colorizers[Random.nextInt(colorizers.size)])

        fractalPanel = SelectablePanel(painter).apply {
            background = Color.WHITE
        }
        
        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(fractalPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(4)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(fractalPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(4)
            )
        }
    }
}