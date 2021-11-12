package ru.smak.ui.painting

import ru.smak.ui.GraphicsPanel
import java.awt.Color
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter

class SelectablePanel(vararg painters: Painter) : GraphicsPanel(*painters){

    private var pt1: Point? = null
    private var pt2: Point? = null

    init {
        addMouseListener(object : MouseAdapter(){

            override fun mousePressed(e: MouseEvent?) {
                graphics.apply {
                    setXORMode(Color.WHITE)
                    fillRect(2*width, 0, 1, 1)
                    setPaintMode()
                }
                pt1 = e?.point
            }

            override fun mouseReleased(e: MouseEvent?) {
                pt1 = null
                pt2 = null
                graphics.setPaintMode()
            }

        })

        addMouseMotionListener(object : MouseMotionAdapter(){
            override fun mouseDragged(e: MouseEvent?) {
                with (graphics){
                    setXORMode(Color.WHITE)
                    pt1?.let { pt ->
                        pt2?.let{ pt2 ->
                            drawRect(pt.x, pt.y, pt2.x - pt.x, pt2.y - pt.y)
                        }
                        pt2 = e?.point
                        e?.let { e ->
                            drawRect(pt.x, pt.y, e.x - pt.x, e.y - pt.y)
                        }
                    }
                    setPaintMode()
                }
            }

        })
    }
}