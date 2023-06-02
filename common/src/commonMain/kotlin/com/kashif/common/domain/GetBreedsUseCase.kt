package com.kashif.common.domain

import com.kashif.common.data.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class GetBreedsUseCase : KoinComponent {
    private val breedsRepository: BreedsRepository = get()

    suspend operator fun invoke(): Any = breedsRepository.get()
}