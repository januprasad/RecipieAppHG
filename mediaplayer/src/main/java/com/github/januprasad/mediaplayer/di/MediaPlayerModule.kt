package com.github.januprasad.mediaplayer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.github.januprasad.mediaplayer.navigation.MediaPlayerFeatureAPi
import com.github.januprasad.mediaplayer.navigation.MediaPlayerImpl

@InstallIn(SingletonComponent::class)
@Module
object MediaPlayerModule {


    @Provides
    fun provideMediaPlayerFeatureApi(): MediaPlayerFeatureAPi {
        return MediaPlayerImpl()
    }

}