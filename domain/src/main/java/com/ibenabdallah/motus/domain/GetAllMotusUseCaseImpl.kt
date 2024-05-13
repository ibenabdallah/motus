package com.ibenabdallah.motus.domain

import com.ibenabdallah.motus.domain.model.Letter
import com.ibenabdallah.motus.domain.model.Status
import com.ibenabdallah.motus.domain.repository.MotusRepository
import javax.inject.Inject

class GetAllMotusUseCaseImpl @Inject constructor(private val repository: MotusRepository) :
    GetAllMotusUseCase {
    override suspend fun invoke(): Result<List<List<Letter>>> {
        val response = repository.invoke()
        val result = when {
            response.isSuccess -> {
                val data = response.getOrNull()
                if (data.isNullOrEmpty()) {
                    Result.success(listOf())
                } else {
                    val mots: List<String> = data.trim().split("\n")
                    val isEmpty = mots.all { it.isEmpty() }
                    if (isEmpty) {
                        Result.success(listOf())
                    } else {
                        Result.success(mots.map { parseStringToLetter(it) })
                    }
                }
            }

            else -> Result.failure(response.exceptionOrNull() ?: Throwable())
        }

        return result
    }

    private fun parseStringToLetter(mot: String): List<Letter> {
        return mot.trim().toList().map { char ->
            Letter(char, Status.None)
        }
    }

}