package ui.anwesome.com.powerbuttonmoveview

import android.content.Context
import android.graphics.*
import android.view.*
/**
 * Created by anweshmishra on 30/03/18.
 */
class PowerButtonMoveView (ctx : Context) : View(ctx) {
    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                
            }
        }
        return true
    }
}