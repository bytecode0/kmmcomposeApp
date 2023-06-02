package com.kashif.common.di

import com.kashif.common.data.BreedsRemoteSource
import com.kashif.common.data.BreedsRepository
import com.kashif.common.data.IBreedsRemoteSource
import com.kashif.common.data.api.BreedsApi
import com.kashif.common.domain.GetBreedsUseCase
import com.kashif.common.platformModule
import com.kashif.common.util.INetworkConnectivityChecker
import com.kashif.common.util.NetworkConnectivityChecker
import com.kashif.common.util.getDispatcherProvider
import com.kashif.common.view.MainViewModel
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    modules(sharedModules)
    appDeclaration()
}

// called by iOS etc
fun initKoin(baseUrl: String) = startKoin {
    modules(sharedModules)
}

private val utilityModule = module {
    factory { getDispatcherProvider() }
}

private val apiModule = module {
    single { createJson() }

    single { createHttpClient(get(), get(), enableNetworkLogs = false) }

    single<INetworkConnectivityChecker> { NetworkConnectivityChecker() }

    factory { BreedsApi() }
}

private val repositoryModule = module {
    factory { BreedsRepository() }

    factory<IBreedsRemoteSource> { BreedsRemoteSource(get(), get()) }
}

private val useCaseModule = module {
    factory { GetBreedsUseCase() }
}

private val viewModelModule = module {
    factory { MainViewModel() }
}

private val sharedModules = listOf(viewModelModule, useCaseModule, repositoryModule, apiModule, utilityModule)


fun commonModule(
    enableNetworkLogs: Boolean,
    baseUrl: String,
) =   platformModule() + getDataModule(enableNetworkLogs, baseUrl)

fun getDataModule(
    enableNetworkLogs: Boolean,
    baseUrl: String,
) = module {

    single { createJson() }

    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }

}

fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean) =
    HttpClient(httpClientEngine) {
        // exception handling
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 10000
        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryIf { _, response -> !response.status.isSuccess() }
            retryOnExceptionIf { _, cause -> cause is HttpRequestTimeoutException }
            delayMillis { 3000L } // retries in 3, 6, 9, etc. seconds
        }

        install(HttpCallValidator) {
            handleResponseException { cause -> println("exception $cause") }
        }

        install(ContentNegotiation) { json(json) }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

fun createJson() = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
    prettyPrint = true
    coerceInputValues = true
}
