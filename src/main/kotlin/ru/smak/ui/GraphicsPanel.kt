package ru.smak.ui

import ru.smak.ui.painting.Painter
import java.awt.Graphics
import javax.swing.JPanel

class GraphicsPanel(val painters: List<Painter>) : JPanel() {

    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.let{
            painters.forEach { p->
                p.paint(it)
            }
        }
    }
}