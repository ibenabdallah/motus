package com.ibenabdallah.motus.ui

import com.ibenabdallah.motus.domain.model.Letter
import com.ibenabdallah.motus.domain.model.Status

internal fun validate(
    current: List<Letter>,
    essay: List<Letter>,
): List<Letter> {

    val result = essay.toMutableList()

    // Identifier les lettres correctes et les lettres mal placées
    for (i in current.indices) {
        if (current[i].letter == result[i].letter) {
            result[i] = result[i].copy(status = Status.Correct)
        } else {
            for (j in result.indices) {
                if (result[j].status == Status.None && current[i].letter == result[j].letter) {
                    result[j] = result[j].copy(status = Status.WrongPlace)
                    break
                }
            }
        }
    }

    // Identifier les lettres incorrectes
    for (i in result.indices) {
        if (result[i].status == Status.None) {
            result[i] = result[i].copy(status = Status.Wrong)
        }
    }
    return result
}

internal fun List<Letter>.isCorrect() = all { it.status == Status.Correct }

internal fun parseStringToLetter(mot: String): List<Letter> =
    mot.trim().toList().map { char -> Letter(char.uppercaseChar(), Status.None) }