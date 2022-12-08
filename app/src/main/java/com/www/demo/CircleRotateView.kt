package com.www.demo

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import androidx.core.graphics.withSave

/**
 * 类描述：
 * 创建人：wh
 * 创建时间：2022/12/7 10:47
 */
class CircleRotateView constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs, 0) {

    companion object {
        const val WIDTH = 400
        const val HEIGHT = WIDTH
        const val RADIUS = 40f
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val rectF by lazy {
        val left = RADIUS / 2f
        val top = RADIUS / 2f
        val right = left + WIDTH - RADIUS
        val bottom = top + HEIGHT - RADIUS
        RectF(left, top, right, bottom)
    }

    init {
//        outlineProvider = object : ViewOutlineProvider() {
//            override fun getOutline(view: View, outline: Outline) {
////                outline.setRoundRect(0, 0, view.width, view.height, RADIUS)
//                outline.setOval(0, 0, view.width, view.height)
//            }
//
//        }
//        clipToOutline = true

    }

    private val path by lazy {
        Path().also {
            //it.addRoundRect(rectF, RADIUS, RADIUS, Path.Direction.CCW)
            it.addArc(rectF, 0f, 90f)
        }
    }

    private val color1 by lazy {
        LinearGradient(
            width * 1f, height / 2f, width * 1f, height * 1f,
            intArrayOf(Color.TRANSPARENT, Color.RED), floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
    }

    private val color2 by lazy {
        LinearGradient(
            width / 2f, height / 2f, width / 2f, 0f,
            intArrayOf(Color.TRANSPARENT, Color.GREEN), floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = resolveSize(WIDTH, widthMeasureSpec)
        val h = resolveSize(HEIGHT, heightMeasureSpec)
        setMeasuredDimension(w, h)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {
        val left = rectF.left + rectF.width() / 2f
        val right = rectF.right
        val top = rectF.top + rectF.height() / 2f
        val bottom = rectF.bottom
        // withSave 保存画布
        canvas.withSave {
//            canvas.clipOutPath(path)

            paint.color = Color.BLUE
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = RADIUS / 2
            canvas.drawArc(rectF, 0f, 360f, false, paint)

            // 画布中心点旋转
            canvas.rotate(currentSpeed, width / 2f, height / 2f)
            // 绘制渐变view1
            paint.shader = color1
//            paint.color = Color.GREEN
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = RADIUS / 2
            canvas.drawArc(rectF, 0f, 90f, false, paint)
            paint.shader = null

//            paint.color = Color.RED
            paint.shader = color2
            canvas.drawArc(rectF, 180f, 90f, false, paint)
            paint.shader = null
        }


    }

    val animator: ObjectAnimator by lazy {
        val animator = ObjectAnimator.ofFloat(this, "currentSpeed", 0f, 360f)
        animator.repeatCount = -1
        animator.interpolator = null
        animator.duration = 2000L
        animator
    }

    private var currentSpeed = 0f
        set(value) {
            field = value
            invalidate()
        }
}