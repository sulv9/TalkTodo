package com.sulv9.talktodo.util

import android.app.Activity
import android.os.Build
import android.view.View

fun Activity.setLightStatusBar(isLightingColor: Boolean) {
    val window = this.window
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (isLightingColor) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
}