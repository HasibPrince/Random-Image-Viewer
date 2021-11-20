package com.e.randomimageviewer.data.repository

import android.graphics.Bitmap
import com.e.randomimageviewer.data.Result

interface IFileRepository {
    fun saveImage(bitmap: Bitmap): Result<String>
    fun getImagePath(): String
}