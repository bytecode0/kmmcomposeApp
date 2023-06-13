package com.dogify.common.data

import com.dogify.common.domain.Breed
import com.dogify.common.util.INetworkConnectivityChecker
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import com.dogify.common.domain.Result

class BreedsRepository: KoinComponent {
    private val remoteSource: IBreedsRemoteSource = get(null)
    private val networkConnectivityChecker: INetworkConnectivityChecker = get(null)
    private val breeds = mutableListOf<Breed>()

    internal suspend fun get() = supervisorScope {
        if (networkConnectivityChecker.isConnected().not()) {
            return@supervisorScope Result.NetworkError(message = "No internet connection")
        }

        remoteSource.getBreeds().map {
            async { Breed(name = it, imageUrl = remoteSource.getBreedImage(it), isFavourite = false) }
        }.awaitAll().also {
            breeds.clear()
            breeds.addAll(it)
            return@supervisorScope Result.Success(breeds)
        }
    }

}