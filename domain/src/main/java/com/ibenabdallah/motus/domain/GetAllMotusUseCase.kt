package com.ibenabdallah.motus.domain

import com.ibenabdallah.motus.domain.model.Letter

interface GetAllMotusUseCase {
    suspend operator fun invoke(): Result<List<List<Letter>>>
}