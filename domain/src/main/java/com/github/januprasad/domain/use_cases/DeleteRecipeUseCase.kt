package com.github.januprasad.domain.use_cases

import com.github.januprasad.domain.model.Recipe
import com.github.januprasad.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteRecipeUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    operator fun invoke(recipe: Recipe) = flow<Unit> {
        searchRepository.deleteRecipe(recipe)
    }.flowOn(Dispatchers.IO)
}