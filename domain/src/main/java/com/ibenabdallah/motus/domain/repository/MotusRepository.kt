package com.ibenabdallah.motus.domain.repository


interface MotusRepository {
    suspend operator fun invoke(): Result<String>
}