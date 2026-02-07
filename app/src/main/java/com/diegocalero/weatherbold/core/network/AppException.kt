package com.diegocalero.weatherbold.core.network

sealed class AppException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    data class NetworkException(
        override val message: String = "Network connection error. Please check your internet connection.",
    ) : AppException(message)

    data class ServerException(
        val code: Int,
        override val message: String = "Server error. Please try again later.",
    ) : AppException(message)

    data class UnknownException(
        override val message: String = "An unexpected error occurred.",
        override val cause: Throwable? = null,
    ) : AppException(message, cause)
}
