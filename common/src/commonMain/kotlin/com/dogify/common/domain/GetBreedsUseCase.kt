package com.dogify.common.domain

import com.dogify.common.data.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class GetBreedsUseCase : KoinComponent {
    private val breedsRepository: BreedsRepository = get()

    suspend operator fun invoke(): Any = breedsRepository.get()
}