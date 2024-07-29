package com.github.januprasad.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = false)
    val idMeal: String,
    val strArea: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val strTags: String,
    val strYoutube: String,
    val strInstructions: String,
)

data class RecipeDetails(
    val idMeal: String,
    val strArea: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val strTags: String,
    val strYoutube: String,
    val strInstructions: String,
    val ingredientsPair: List<Pair<String, String>>
)

fun RecipeDetails.toRecipe(): Recipe {
    return Recipe(
        idMeal,
        strArea,
        strMeal,
        strMealThumb,
        strCategory,
        strTags,
        strYoutube,
        strInstructions
    )
}