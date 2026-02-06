package com.diegocalero.weatherbold.core.network

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall())
    } catch (e: IOException) {
        Result.Error(AppException.NetworkException())
    } catch (e: HttpException) {
        Result.Error(
            AppException.ServerException(
                code = e.code(),
                message = e.message() ?: "Server error"
            )
        )
    } catch (e: Exception) {
        Result.Error(
            AppException.UnknownException(
                message = e.message ?: "Unknown error",
                cause = e
            )
        )
    }
}