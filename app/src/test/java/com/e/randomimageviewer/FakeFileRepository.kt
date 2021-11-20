package com.e.randomimageviewer

import android.graphics.Bitmap
import com.e.randomimageviewer.data.repository.IFileRepository
import java.io.File
import com.e.randomimageviewer.data.Result

class FakeFileRepository : IFileRepository {
    private val imagePath = "/data/user/0/com.e.randomimageviewer/files/images/randomImage.jpg"

    override fun saveImage(bitmap: Bitmap): Result<File> {
        if(bitmap.width == 0 || bitmap.height == 0) {
            return Result.error("Failed!");
        }
        return Result.success(File(imagePath))
    }

    override fun getImagePath(): File {
        return File(imagePath)
    }

}