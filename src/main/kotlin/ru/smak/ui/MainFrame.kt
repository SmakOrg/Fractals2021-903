package ru.smak.ui

import ru.smak.math.fractals.Mandelbrot
import ru.smak.ui.painting.CartesianPlane
import ru.smak.ui.painting.fractals.FractalPainter
import ru.smak.ui.painting.fractals.grayFractal
import ru.smak.ui.painting.fractals.pinkFractal
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.GroupLayout
import javax.swing.JFrame

class MainFrame : JFrame() {

    val fractalPanel: GraphicsPanel

    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(600, 400)
        val plane = CartesianPlane(-2.0, 1.0, -1.0, 1.0)

        val colorizers = listOf(::grayFractal, ::pinkFractal)

        fractalPanel = GraphicsPanel(
            listOf(
                FractalPainter(Mandelbrot, plane, colorizers[0])
            )
        ).apply {
            background = Color.WHITE
            addComponentListener(object: ComponentAdapter(){
                override fun componentResized(e: ComponentEvent?) {
                    plane.pixelSize = size
                    repaint()
                }
            })
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
        pack()
        plane.pixelSize = fractalPanel.size
    }
}