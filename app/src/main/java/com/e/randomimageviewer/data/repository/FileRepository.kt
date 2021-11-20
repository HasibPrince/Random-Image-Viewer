package com.e.randomimageviewer.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.e.randomimageviewer.data.FileInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FileRepository @Inject constructor(@ApplicationContext private val context: Context) : IFileRepository {
    override fun saveImage(bitmap: Bitmap): Result<String> {
        return Result.success(getImagePath())
    }

    override fun getImagePath(): String {
        return FileInfo.getFileInfo(context = context)
    }

}