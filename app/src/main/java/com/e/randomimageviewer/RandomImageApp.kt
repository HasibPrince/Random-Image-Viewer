package com.e.randomimageviewer

import androidx.multidex.MultiDexApplication
import com.e.randomimageviewer.common.AppConnectivityManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RandomImageApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AppConnectivityManager.instance.init(this)
    }
}