package com.dogify.common.data

import com.dogify.common.data.api.BreedsApi
import com.dogify.common.util.DispatcherProvider
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