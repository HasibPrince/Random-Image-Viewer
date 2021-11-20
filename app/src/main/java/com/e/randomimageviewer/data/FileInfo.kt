package com.e.randomimageviewer.data

import android.content.Context
import java.io.File

object FileInfo {
    fun getFileInfo(context: Context): String {
        val imageDirectory: String = context.filesDir.absolutePath + File.separator + "images/"
        return imageDirectory + "randomImage.jpg"
    }
}