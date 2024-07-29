package com.github.januprasad.searchrecipieapp.di

import android.content.Context
import com.github.januprasad.data.local.RecipeDao
import com.github.januprasad.features.navigation.SearchFeatureApi
import com.github.januprasad.mediaplayer.navigation.MediaPlayerFeatureAPi
import com.github.januprasad.searchrecipieapp.local.AppDatabase
import com.github.januprasad.searchrecipieapp.navigation.NavigationSubGraphs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideNavigationSubGraphs(
        searchFeatureApi: SearchFeatureApi,
        mediaPlayerFeatureAPi: MediaPlayerFeatureAPi
    ): NavigationSubGraphs {
        return NavigationSubGraphs(searchFeatureApi, mediaPlayerFeatureAPi)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

    @Provides
    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDao {
        return appDatabase.getRecipeDao()
    }
}