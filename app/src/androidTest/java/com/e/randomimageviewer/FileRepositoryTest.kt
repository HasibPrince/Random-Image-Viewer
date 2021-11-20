package com.e.randomimageviewer

import android.app.Application
import android.graphics.Bitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.e.randomimageviewer.data.repository.FileRepository
import com.e.randomimageviewer.data.Result
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FileRepositoryTest {
    lateinit var fileRepository: FileRepository

    @Before
    fun setup() {
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application
        fileRepository = FileRepository(app)
    }

    private val imagePath = "/data/user/0/com.e.randomimageviewer/files/images/randomImage.jpg"

    @Test
    fun imagePathTest() {
        // Context of the app under test.
        assertEquals(
            imagePath,
            fileRepository.getImagePath().absolutePath)
    }

    @Test
    fun saveImageTest() {
        val result = fileRepository.saveImage(Bitmap.createBitmap(100,100, Bitmap.Config.RGB_565))
        assertEquals(Result.Status.SUCCESS, result.status)
        assertEquals(imagePath, result.data?.absolutePath)
    }
}