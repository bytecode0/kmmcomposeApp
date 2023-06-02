package com.kashif.common.data.api

import com.kashif.common.data.KtorApi
import com.kashif.common.data.api.model.BreedImageResponse
import com.kashif.common.data.api.model.BreedsResponse
import io.ktor.client.call.*
import io.ktor.client.request.*

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