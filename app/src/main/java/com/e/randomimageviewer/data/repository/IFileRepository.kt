package com.e.randomimageviewer.data.repository

import android.graphics.Bitmap
import com.e.randomimageviewer.data.Result
import java.io.File

interface IFileRepository {
    fun saveImage(bitmap: Bitmap): Result<File>
    fun getImagePath(): File
}