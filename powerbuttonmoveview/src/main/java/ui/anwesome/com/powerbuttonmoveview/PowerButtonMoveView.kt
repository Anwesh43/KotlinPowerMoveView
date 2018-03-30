package ui.anwesome.com.powerbuttonmoveview

import android.content.Context
import android.graphics.*
import android.view.*

/**
 * Created by anweshmishra on 30/03/18.
 */
class PowerButtonMoveView(ctx: Context) : View(ctx) {
    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas: Canvas) {

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var prevScale: Float = 0f, var dir: Int = 0, var j: Int = 0) {
        val scales: Array<Float> = arrayOf(0f, 0f, 0f, 0f)
        fun update(stopcb: (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if (Math.abs(scales[j] - prevScale) > 1) {
                prevScale = scales[j] + dir
                j += dir
                if (j == scales.size || j == -1) {
                    j -= dir
                    dir = 0
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }

        fun startUpdating(startcb: () -> Unit) {
            if (dir == 0) {
                dir = 1 - 2 * prevScale.toInt()
                startcb()
            }
        }
    }

    data class Animator(var view: View, var animated: Boolean = false) {
        fun animate(updatecb: () -> Unit) {
            if (animated) {
                try {
                    updatecb()
                    Thread.sleep(50)
                    view.invalidate()
                } catch (ex: Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class PowerButtonMove(var i: Int, val state: State = State()) {
        fun draw(canvas: Canvas, paint: Paint) {
            paint.color = Color.WHITE
            val w: Float = canvas.width.toFloat()
            val h: Float = canvas.height.toFloat()
            val r = Math.min(w, h) / 18
            paint.strokeWidth = r / 3
            val deg: Float = 15f
            canvas.save()
            canvas.translate(w / 2, h / 2)
            canvas.rotate(90f * state.scales[2])
            val y_updated = (h / 2) * state.scales[3]
            canvas.drawLine(-y_updated, 0f, -y_updated + (r + r / 6) * state.scales[1], 0f, paint)
            canvas.drawArc(RectF(-r, -r, r, r), deg, (360f - 2 * deg) * state.scales[0], false, paint)
            canvas.restore()
        }

        fun update(stopcb: (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb: () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer(var view : PowerButtonMoveView) {
        val animator : Animator = Animator(view)
        val powerButtonMove : PowerButtonMove = PowerButtonMove(0)
        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            powerButtonMove.draw(canvas, paint)
            animator.animate {
                powerButtonMove.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            powerButtonMove.startUpdating {
                animator.start()
            }
        }
    }
}