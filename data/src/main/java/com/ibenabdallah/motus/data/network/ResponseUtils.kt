package com.ibenabdallah.motus.data.network

import retrofit2.Response


private inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (isSuccessful) body()?.run(action)
    return this
}

private inline fun <T : Any> Response<T>.onFailure(action: (Throwable) -> Unit) {
    if (!isSuccessful) errorBody()?.run { action(Throwable(message())) }
}

internal fun <I : Any, O : Any> Response<I>.toStatus(transform: (I) -> Result<O>): Result<O> {
    try {
        onSuccess { return transform(it) }
        onFailure { return Result.failure(it) }
        return Result.failure(Throwable("Not found"))
    } catch (e: Exception) {
        return Result.failure(e)
    }
}