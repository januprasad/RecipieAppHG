package com.github.januprasad.data.di

import com.github.januprasad.data.local.RecipeDao
import com.github.januprasad.data.remote.SearchApiService
import com.github.januprasad.data.repository.SearchRepoImpl
import com.github.januprasad.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://www.themealdb.com/"

@InstallIn(SingletonComponent::class)
@Module
object SearchDataModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideSearchApiService(retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }

    @Provides
    fun provideSearchRepo(
        searchApiService: SearchApiService,
        recipeDao: RecipeDao
    ): SearchRepository {
        return SearchRepoImpl(searchApiService, recipeDao)
    }


}