package com.sulv9.talktodo.util

import android.content.res.Resources.getSystem

val Int.dp: Int get() = (this / getSystem().displayMetrics.density).toInt()

val Int.dpToPx: Int get() = (this * getSystem().displayMetrics.density).toInt()

val Int.sp: Int get() = (this / getSystem().displayMetrics.scaledDensity).toInt()

val Int.spToPx: Int get() = (this * getSystem().displayMetrics.scaledDensity).toInt()

val Float.dp: Float get() = this / getSystem().displayMetrics.density

val Float.dpToPx: Float get() = this * getSystem().displayMetrics.density

val Float.sp: Float get() = this / getSystem().displayMetrics.scaledDensity

val Float.spToPx: Float get() = this * getSystem().displayMetrics.scaledDensity