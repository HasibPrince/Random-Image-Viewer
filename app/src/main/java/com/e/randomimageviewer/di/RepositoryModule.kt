package com.e.randomimageviewer.di

import com.e.randomimageviewer.data.repository.FileRepository
import com.e.randomimageviewer.data.repository.IFileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun getFileRepository(fileRepository: FileRepository): IFileRepository
}