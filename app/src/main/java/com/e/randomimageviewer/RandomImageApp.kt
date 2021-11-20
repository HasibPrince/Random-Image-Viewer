package com.e.randomimageviewer

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RandomImageApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
    }
}