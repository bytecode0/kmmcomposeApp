package com.dogify.common.view

import com.dogify.common.domain.Breed

sealed class BreedsUIState<out Any> {
    object Loading : BreedsUIState<Nothing>()
    data class Success(val breeds: List<Breed>): BreedsUIState<List<Breed>>()
    data class Error(val errorDescription: String) : BreedsUIState<String>()
}