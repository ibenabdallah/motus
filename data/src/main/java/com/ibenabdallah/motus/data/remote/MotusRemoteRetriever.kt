package com.ibenabdallah.motus.data.remote

interface MotusRemoteRetriever {
    suspend operator fun invoke(): Result<String>
}