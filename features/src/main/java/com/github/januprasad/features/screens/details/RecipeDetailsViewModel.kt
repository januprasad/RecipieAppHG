package com.github.januprasad.features.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.januprasad.common.nav.UiText
import com.github.januprasad.common.utils.NetworkResult
import com.github.januprasad.domain.model.RecipeDetails
import com.github.januprasad.domain.model.toRecipe
import com.github.januprasad.domain.use_cases.DeleteRecipeUseCase
import com.github.januprasad.domain.use_cases.GetRecipeDetailsUseCase
import com.github.januprasad.domain.use_cases.InsertRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val insertRecipeUseCase: InsertRecipeUseCase
) :
    ViewModel() {

    private val _uiState =
        MutableStateFlow(RecipeDetailsScreen.UiState())
    val uiState: StateFlow<RecipeDetailsScreen.UiState> get() = _uiState.asStateFlow()

    private val _navigation =
        Channel<RecipeDetailsScreen.Navigation>()
    val navigation: Flow<RecipeDetailsScreen.Navigation> get() = _navigation.receiveAsFlow()

    fun onEvent(event: RecipeDetailsScreen.Event) {
        when (event) {
            is RecipeDetailsScreen.Event.FetchRecipeDetails -> recipeDetails(
                event.id
            )

            RecipeDetailsScreen.Event.GoToRecipeListScreen -> viewModelScope.launch {
                _navigation.send(RecipeDetailsScreen.Navigation.GoToRecipeListScreen)
            }

            is RecipeDetailsScreen.Event.DeleteRecipe -> {
                deleteRecipeUseCase.invoke(event.recipeDetails.toRecipe())
                    .launchIn(viewModelScope)

            }

            is RecipeDetailsScreen.Event.InsertRecipe -> {
                insertRecipeUseCase.invoke(event.recipeDetails.toRecipe())
                    .launchIn(viewModelScope)
            }

            is RecipeDetailsScreen.Event.GoToMediaPlayer -> {
                viewModelScope.launch {
                    _navigation.send(
                        RecipeDetailsScreen.Navigation.GoToMediaPlayer(
                            event.youtubeUrl
                        )
                    )
                }
            }
        }
    }

    private fun recipeDetails(id: String) = getRecipeDetailsUseCase.invoke(id)
        .onEach { result ->
            when (result) {
                is NetworkResult.Error -> {
                    _uiState.update {
                        RecipeDetailsScreen.UiState(
                            error = UiText.RemoteString(result.message.toString())
                        )
                    }
                }

                is NetworkResult.Loading -> _uiState.update {
                    RecipeDetailsScreen.UiState(
                        isLoading = true
                    )
                }

                is NetworkResult.Success -> _uiState.update {
                    RecipeDetailsScreen.UiState(
                        data = result.data
                    )
                }

            }

        }.launchIn(viewModelScope)

}

object RecipeDetailsScreen {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: RecipeDetails? = null
    )

    sealed interface Navigation {
        data object GoToRecipeListScreen : Navigation
        data class GoToMediaPlayer(val youtubeUrl: String) : Navigation
    }

    sealed interface Event {

        data class FetchRecipeDetails(val id: String) : Event

        data class InsertRecipe(val recipeDetails: RecipeDetails) : Event
        data class DeleteRecipe(val recipeDetails: RecipeDetails) : Event

        data object GoToRecipeListScreen : Event

        data class GoToMediaPlayer(val youtubeUrl: String) : Event

    }

}
