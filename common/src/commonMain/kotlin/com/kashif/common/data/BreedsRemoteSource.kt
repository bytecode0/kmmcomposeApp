package com.kashif.common.data

import com.kashif.common.data.api.BreedsApi
import com.kashif.common.util.DispatcherProvider
import kotlinx.coroutines.withContext

internal class BreedsRemoteSource(
    private val api: BreedsApi,
    private val dispatcherProvider: DispatcherProvider
) : IBreedsRemoteSource {
    override suspend fun getBreeds() = withContext(dispatcherProvider.io) {
        api.getBreeds().breeds
    }

    override suspend fun getBreedImage(breed: String) = withContext(dispatcherProvider.io) {
        api.getRandomBreedImageFor(breed).breedImageUrl
    }
}