package com.ibenabdallah.motus.domain.model

sealed interface Status {
    data object None : Status
    data object Correct : Status
    data object Wrong : Status
    data object WrongPlace : Status
}