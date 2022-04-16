package com.sulv9.talktodo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MainApp: Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}