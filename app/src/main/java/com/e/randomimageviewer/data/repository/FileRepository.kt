package com.e.randomimageviewer.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.e.randomimageviewer.common.saveToFile
import com.e.randomimageviewer.data.FileInfo
import com.e.randomimageviewer.data.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class FileRepository @Inject constructor(@ApplicationContext private val context: Context) : IFileRepository {
    override fun saveImage(bitmap: Bitmap): Result<String> {
        val imagePath = FileInfo.getFileInfo(context)
        Log.d(this.javaClass.simpleName, "===> image path: $imagePath")
        val directory = File(FileInfo.getFileDirectory(context))
        if(!directory.exists()) {
            directory.mkdirs()
        }
        val imageFile = File(imagePath)
        if(imageFile.exists()) {
            imageFile.delete()
        }
        return bitmap.saveToFile(imagePath);
    }

    override fun getImagePath(): String {
        return FileInfo.getFileInfo(context = context)
    }

}