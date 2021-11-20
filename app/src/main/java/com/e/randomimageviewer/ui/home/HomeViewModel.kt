package com.e.randomimageviewer.ui.home

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e.randomimageviewer.data.repository.IFileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fileRepository: IFileRepository) : ViewModel() {
    fun saveImage(bitmap: Bitmap) {
        viewModelScope.launch {
            fileRepository.saveImage(bitmap)
        }
    }
}