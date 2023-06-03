package com.kashif.common.view

import com.kashif.common.domain.Breed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import com.kashif.common.domain.GetBreedsUseCase
import com.kashif.common.util.DispatcherProvider
import com.kashif.common.domain.Result

class MainViewModel: KoinComponent {
    private val dispatcherProvider: DispatcherProvider = get()
    private val getBreedsUseCase: GetBreedsUseCase = get()
    private val viewModelScope = CoroutineScope(dispatcherProvider.io)
    private val _breedsStateFlow = MutableStateFlow<BreedsUIState<Any>>(BreedsUIState.Loading)
    val breeds: StateFlow<BreedsUIState<Any>> = _breedsStateFlow

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    private suspend fun loadData() {
        _breedsStateFlow.value = BreedsUIState.Loading
        when(val result = getBreedsUseCase()) {
            is Result.Success<*> -> _breedsStateFlow.value = BreedsUIState.Success(breeds = result.data as List<Breed>)
            is Result.Error -> _breedsStateFlow.value = BreedsUIState.Error(errorDescription = result.message)
            is Result.NetworkError -> _breedsStateFlow.value = BreedsUIState.Error(errorDescription = result.message)
        }
    }

    suspend fun refresh() {
        loadData()
    }
}