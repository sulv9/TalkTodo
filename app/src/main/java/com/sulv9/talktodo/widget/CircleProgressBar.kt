package com.sulv9.talktodo.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.sulv9.talktodo.R
import com.sulv9.talktodo.util.dpToPx
import com.sulv9.talktodo.util.spToPx
import kotlin.math.atan
import kotlin.math.min

class CircleProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_MAX_PROGRESS = 100
        private const val DEFAULT_CURRENT_PROGRESS = 0
        private const val DEFAULT_START_ANGLE = 0f
        private val DEFAULT_PROGRESS_COLOR = Color.parseColor("#3DDC84")
        private val DEFAULT_TEXT_COLOR = Color.parseColor("#000000")
        private val DEFAULT_BG_COLOR = Color.parseColor("#FFFFFF")
        private val DEFAULT_RING_BG_COLOR = Color.parseColor("#DCDCDC")
        private val DEFAULT_TEXT_SIZE = 16f.spToPx
        private val MAX_WIDTH_HEIGHT = 90.dpToPx
        private val DEFAULT_ARC_WIDTH = 12f.dpToPx
    }

    private var max = DEFAULT_MAX_PROGRESS
    private var progress = DEFAULT_CURRENT_PROGRESS
        set(value) {
            field = value
            invalidate()
        }
    private var progressColor = DEFAULT_PROGRESS_COLOR
    private var bgColor = DEFAULT_BG_COLOR
    private var ringBgColor = DEFAULT_RING_BG_COLOR
    private var textColor = DEFAULT_TEXT_COLOR
    private var mTextSize = DEFAULT_TEXT_SIZE

    private var mShader: Shader? = null
    var ringColorArray: IntArray? = null

    private var mArcWidth = 0f
    private var mWidth = 0
    private var mHeight = 0

    private lateinit var mRingPaint: Paint
    private lateinit var mRingBgPaint: Paint
    private lateinit var mBgPaint: Paint
    private lateinit var mTextPaint: Paint
    private lateinit var mRectF: RectF

    fun startAnim(mProgress: Int, mDuration: Long = 1200) {
        ObjectAnimator.ofInt(this, "progress", 0, mProgress).apply {
            duration = mDuration
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener {
                progress = it.animatedValue as Int
            }
            start()
        }
    }

    init {
        initAttrs(attrs)
        initPaints()
    }

    private fun initPaints() {
        mRingPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = mArcWidth
            color = progressColor
            strokeCap = Paint.Cap.ROUND
        }
        mRingBgPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = mArcWidth / 1.5f
            color = ringBgColor
            shader = null
        }
        mBgPaint = Paint().apply {
            isAntiAlias = true
            color = bgColor
        }
        mTextPaint = Paint().apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            color = textColor
            textSize = mTextSize
        }
    }

    private fun initAttrs(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar)
            max = typedArray.getInt(
                R.styleable.CircleProgressBar_max,
                DEFAULT_MAX_PROGRESS
            )
            progress = typedArray.getInt(
                R.styleable.CircleProgressBar_progress,
                DEFAULT_CURRENT_PROGRESS
            )
            progressColor = typedArray.getColor(
                R.styleable.CircleProgressBar_progressColor,
                DEFAULT_PROGRESS_COLOR
            )
            bgColor = typedArray.getColor(
                R.styleable.CircleProgressBar_backgroundColor,
                DEFAULT_BG_COLOR
            )
            textColor = typedArray.getColor(
                R.styleable.CircleProgressBar_textColor,
                DEFAULT_TEXT_COLOR
            )
            mTextSize = typedArray.getDimension(
                R.styleable.CircleProgressBar_textSize,
                DEFAULT_TEXT_SIZE
            )
            mArcWidth = typedArray.getDimension(
                R.styleable.CircleProgressBar_arcWidth,
                DEFAULT_ARC_WIDTH
            )
            ringBgColor = typedArray.getColor(
                R.styleable.CircleProgressBar_ringBgColor,
                DEFAULT_RING_BG_COLOR
            )
            typedArray.recycle()
        }
    }

    /**
     * 支持wrap_content
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec))
    }

    private fun measure(measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        return when (specMode) {
            MeasureSpec.EXACTLY -> {
                specSize
            }
            MeasureSpec.AT_MOST -> {
                min(MAX_WIDTH_HEIGHT, specSize)
            }
            MeasureSpec.UNSPECIFIED -> {
                MAX_WIDTH_HEIGHT
            }
            else -> 0
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = measuredWidth
        mHeight = measuredHeight
        mRectF = RectF(
            mArcWidth / 2.0f, mArcWidth / 2.0f,
            mWidth - mArcWidth / 2.0f, mHeight - mArcWidth / 2.0f
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            // 画背景圆
            drawBackground(it)
            drawArc(it)
            drawText(it)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawCircle(mRectF.centerX(), mRectF.centerY(), mWidth / 2.0f, mBgPaint)
    }

    /**
     * 绘制圆环
     */
    private fun drawArc(canvas: Canvas) {
        canvas.drawArc(mRectF, 0f, 360f, false, mRingBgPaint)
        if (ringColorArray != null && mShader == null) {
            mShader = SweepGradient(mRectF.centerX(), mRectF.centerY(),
                ringColorArray!!, null).apply {
                    setLocalMatrix(Matrix().apply {
                        setRotate(calculateOffset(), mRectF.centerX(), mRectF.centerY())
                    })
            }
            mRingPaint.shader = mShader
        }
        val sweepAngle = progress.toFloat() / max * 360f
        canvas.drawArc(mRectF, DEFAULT_START_ANGLE, sweepAngle, false, mRingPaint)
    }

    private fun drawText(canvas: Canvas) {
        canvas.drawText(
            "${(progress.toFloat() / max * 100).toInt()}%",
            mRectF.centerX(),
            mHeight * 0.58f,
            mTextPaint
        )
    }

    //设置渐变效果时让SweepGradient偏移一定角度来保证圆角凸出部分和圆环进度条颜色一样
    private fun calculateOffset(): Float {
        //计算strokeCap为Paint.Cap.ROUND时圆角凸出部分相当于整个圆环占的比例，半圆的直径 = 线的宽度
        val roundPercent =
            (atan(mArcWidth / 2.0 / (mWidth / 2.0 - mArcWidth / 2.0)) / (2 * Math.PI)).toFloat()
        val currentPercent = progress / max.toFloat()  //当前进度
        return when {
            currentPercent + roundPercent >= 1.0f -> {
                DEFAULT_START_ANGLE
            }
            currentPercent + 2 * roundPercent >= 1.0f -> {
                DEFAULT_START_ANGLE - (1 - currentPercent - roundPercent) * 360f
            }
            else -> {
                DEFAULT_START_ANGLE - roundPercent * 360f
            }
        }
    }

}