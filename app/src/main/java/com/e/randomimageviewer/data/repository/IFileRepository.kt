package com.e.randomimageviewer.data.repository

import android.graphics.Bitmap

interface IFileRepository {
    fun saveImage(bitmap: Bitmap): Result<String>
    fun getImagePath(): String
}