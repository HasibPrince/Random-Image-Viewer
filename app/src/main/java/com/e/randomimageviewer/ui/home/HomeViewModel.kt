package com.e.randomimageviewer.ui.home

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e.randomimageviewer.data.repository.IFileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import com.e.randomimageviewer.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(private val fileRepository: IFileRepository) : ViewModel() {
    private val _imagePath: MutableLiveData<Result<File>> = MutableLiveData()
    val imagePath: LiveData<Result<File>> = _imagePath

    init {
        _imagePath.value = Result.success(fileRepository.getImagePath())
    }

    fun saveImage(bitmap: Bitmap) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _imagePath.postValue(fileRepository.saveImage(bitmap))
            }
        }
    }
}