package com.github.januprasad.features.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.github.januprasad.common.nav.FeatureApi
import com.github.januprasad.common.nav.NavigationRoute
import com.github.januprasad.common.nav.NavigationSubGraphRoute
import com.github.januprasad.features.screens.details.RecipeDetailsScreen
import com.github.januprasad.features.screens.details.RecipeDetailsViewModel
import com.github.januprasad.features.screens.favorite.FavoriteScreen
import com.github.januprasad.features.screens.favorite.FavoriteViewModel
import com.github.januprasad.features.screens.recipe_list.RecipeList
import com.github.januprasad.features.screens.recipe_list.RecipeListScreen
import com.github.januprasad.features.screens.recipe_list.RecipeListViewModel

interface SearchFeatureApi : FeatureApi


class SearchFeatureApiImpl : SearchFeatureApi {
    override fun registerGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navHostController: androidx.navigation.NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoute.Search.route,
            startDestination = NavigationRoute.RecipeList.route
        ) {

            composable(route = NavigationRoute.RecipeList.route) {
                val viewModel = hiltViewModel<RecipeListViewModel>()
                RecipeListScreen(
                    viewModel = viewModel,
                    navHostController = navHostController
                ) { mealId ->
                    viewModel.onEvent(RecipeList.Event.GoToRecipeDetails(mealId))
                }

            }

            composable(route = NavigationRoute.RecipeDetails.route) {
                val viewModel = hiltViewModel<RecipeDetailsViewModel>()
                val mealId = it.arguments?.getString("id")
                LaunchedEffect(key1 = mealId) {
                    mealId?.let {
                        viewModel.onEvent(RecipeDetailsScreen.Event.FetchRecipeDetails(it))
                    }
                }
                RecipeDetailsScreen(
                    viewModel = viewModel,
                    onNavigationClick = {
                        viewModel.onEvent(RecipeDetailsScreen.Event.GoToRecipeListScreen)
                    },
                    onFavoriteClick = {
                        viewModel.onEvent(RecipeDetailsScreen.Event.InsertRecipe(it))
                    },
                    onDelete = {
                        viewModel.onEvent(RecipeDetailsScreen.Event.DeleteRecipe(it))
                    }, navHostController = navHostController
                )
            }

            composable(NavigationRoute.FavoriteScreen.route) {
                val viewModel = hiltViewModel<FavoriteViewModel>()
                FavoriteScreen(
                    navHostController = navHostController,
                    viewModel = viewModel,
                    onClick = { mealId ->
                        viewModel.onEvent(FavoriteScreen.Event.GoToDetails(mealId))
                    })
            }

        }


    }
}

