package com.e.randomimageviewer.common

import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import com.e.randomimageviewer.data.Result

fun Bitmap.saveToFile(path: String): Result<File> {
    return try {
        val file = File(path)
        file.parentFile?.mkdirs()
        FileOutputStream(file).use { outputStream ->
            compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.flush()
        }
        Result.success(File(file.absolutePath))
    } catch (e: Exception) {
        Log.e("Bitmap saving","===> selfie saving failed at path $path with error: ${e.localizedMessage}")
        return Result.error(e.message ?: "Unknown Error")
    }
}