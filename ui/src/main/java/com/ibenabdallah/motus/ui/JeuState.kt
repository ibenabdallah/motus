package com.ibenabdallah.motus.ui

import androidx.compose.runtime.Immutable

@Immutable
sealed interface JeuState {
    data object Failure : JeuState
    data object Success : JeuState
}