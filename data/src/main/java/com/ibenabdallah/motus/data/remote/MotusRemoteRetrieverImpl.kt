package com.ibenabdallah.motus.data.remote


import com.ibenabdallah.motus.data.di.RemoteDataSourceCoroutineContext
import com.ibenabdallah.motus.data.network.MotusApi
import com.ibenabdallah.motus.data.network.toStatus
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal class MotusRemoteRetrieverImpl @Inject constructor(
    private val api: MotusApi,
    @RemoteDataSourceCoroutineContext private val coroutineContext: CoroutineContext,
) : MotusRemoteRetriever {

    override suspend fun invoke(): Result<String> {
        return withContext(coroutineContext) {
            api.getAll().toStatus { Result.success(it) }
        }
    }
}