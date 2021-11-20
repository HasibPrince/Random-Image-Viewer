package com.e.randomimageviewer.data

import android.content.Context
import java.io.File

object FileInfo {
    fun getFileDirectory(context: Context): String {
        return  context.filesDir.absolutePath + File.separator + "images/"
    }
    fun getFileInfo(context: Context): String {
        return getFileDirectory(context) + "randomImage.jpg"
    }
}