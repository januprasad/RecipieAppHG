package com.github.januprasad.features.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.github.januprasad.features.navigation.SearchFeatureApi
import com.github.januprasad.features.navigation.SearchFeatureApiImpl

@InstallIn(SingletonComponent::class)
@Module
object UiModule {


    @Provides
    fun provideSearchFeatureApi(): SearchFeatureApi {
        return SearchFeatureApiImpl()
    }

}