package com.example.paymenmethodexam.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Log.e("SafeApiCall", "${throwable.code()}: ${throwable.response()?.errorBody()} ")
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Log.e("SafeApiCall", throwable.message.toString())
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}