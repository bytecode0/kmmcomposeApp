package com.dogify.common.domain

sealed class Result<out T> {
    class Success<out T>(val data: T) : Result<T>()
    class Error(val message: String) : Result<Nothing>()
    class NetworkError(val message: String) : Result<Nothing>()
}