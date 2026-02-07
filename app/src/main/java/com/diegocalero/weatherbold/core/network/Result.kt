package com.diegocalero.weatherbold.core.network

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()

    data class Error(val exception: AppException) : Result<Nothing>()

    data object Loading : Result<Nothing>()
}
