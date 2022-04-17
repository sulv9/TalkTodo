package com.sulv9.talktodo.util

import android.text.SpannableString
import android.text.style.StrikethroughSpan

fun String.strike(start: Int = 0, end: Int = this.length) =
    SpannableString(this).apply {
        setSpan(StrikethroughSpan(), start, end, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
    }