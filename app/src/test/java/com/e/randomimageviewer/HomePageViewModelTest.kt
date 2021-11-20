package com.e.randomimageviewer

import MainCoroutineRule
import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.e.randomimageviewer.ui.home.HomeViewModel
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import com.e.randomimageviewer.data.Result
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomePageViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var fakeFileRepository: FakeFileRepository
    lateinit var homeViewModel: HomeViewModel
    private val imagePath = "/data/user/0/com.e.randomimageviewer/files/images/randomImage.jpg"

    @Mock
    lateinit var bitmap: Bitmap

    @Before
    fun setup() {
        fakeFileRepository = FakeFileRepository()
        homeViewModel = HomeViewModel(fakeFileRepository)
    }

    @Test
    fun saveBitmapTest() = mainCoroutineRule.runBlockingTest {
        bitmap.width = 100
        bitmap.height = 100
        homeViewModel.saveImage(bitmap)
        var resultLiveData = homeViewModel.imagePath.getOrAwaitValue(100)
        assertEquals(Result.Status.SUCCESS, resultLiveData.status)
        assertEquals(imagePath, resultLiveData.data?.absolutePath)
    }
}