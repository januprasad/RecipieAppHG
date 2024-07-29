package com.github.januprasad.domain.use_cases

import com.github.januprasad.domain.repository.SearchRepository
import javax.inject.Inject

class GetAllRecipesFromLocalDbUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    operator fun invoke() = searchRepository.getAllRecipes()
}