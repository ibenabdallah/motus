package com.ibenabdallah.motus.data

import com.ibenabdallah.motus.data.remote.MotusRemoteRetriever
import com.ibenabdallah.motus.domain.repository.MotusRepository
import javax.inject.Inject

class MotusRepositoryImpl @Inject constructor(
    private val docRetriever: MotusRemoteRetriever,
) : MotusRepository {

    override suspend fun invoke(): Result<String> = docRetriever.invoke()

}