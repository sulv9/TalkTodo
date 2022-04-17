package com.sulv9.talktodo.util

import java.text.SimpleDateFormat
import java.util.*

private val dayFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
private val timeFormat = SimpleDateFormat("HH:MM:SS", Locale.CHINA)
private val datetimeFormat = SimpleDateFormat("yyyy-MM-dd HH:MM:SS", Locale.CHINA)

fun Long.toDay() = dayFormat.format(Date(this))

fun Long.toTime() = timeFormat.format(Date(this))

fun Long.toDatetime() = datetimeFormat.format(Date(this))