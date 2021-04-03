package com.example.myapplication.app

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseMode.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()

    }
}