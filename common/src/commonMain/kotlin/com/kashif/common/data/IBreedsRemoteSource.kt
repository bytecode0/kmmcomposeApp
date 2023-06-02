package com.kashif.common.data

interface IBreedsRemoteSource {
    suspend fun getBreeds(): List<String>

    suspend fun getBreedImage(breed: String): String
}