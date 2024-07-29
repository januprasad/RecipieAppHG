package com.github.januprasad.domain.repository

import com.github.januprasad.domain.model.Recipe
import com.github.januprasad.domain.model.RecipeDetails
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    /**
     * remote
     */
    suspend fun getRecipes(s: String): Result<List<Recipe>>

    suspend fun getRecipeDetails(id: String): Result<RecipeDetails>

    /**
     * db
     */
    suspend fun insertRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    fun getAllRecipes(): Flow<List<Recipe>>


}