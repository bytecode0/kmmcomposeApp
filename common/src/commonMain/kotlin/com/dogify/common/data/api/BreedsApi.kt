package com.dogify.common.data.api

import com.dogify.common.data.KtorApi
import com.dogify.common.data.api.model.BreedImageResponse
import com.dogify.common.data.api.model.BreedsResponse
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * Ktor Networking Api for getting information about a Breed entity
 */
internal class BreedsApi : KtorApi() {

    suspend fun getBreeds(): BreedsResponse = client.get {
        apiUrl("breeds/list")
    }.body()

    suspend fun getRandomBreedImageFor(breed: String): BreedImageResponse = client.get {
        apiUrl("breed/$breed/images/random")
    }.body()
}