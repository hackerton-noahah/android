package com.hackerton.noahah.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    init{
        instance =this
    }

    companion object{
        lateinit var instance : App
        lateinit var sharedPreferences: SharedPreferences

        // 앱의 context 를 불러오는 함수
        fun context() : Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences =
            applicationContext.getSharedPreferences("APP", MODE_PRIVATE)
    }

}