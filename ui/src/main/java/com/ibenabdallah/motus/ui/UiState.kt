package com.ibenabdallah.motus.ui

import androidx.compose.runtime.Immutable

@Immutable
sealed interface UiState {

    /**
     * The state is loading.
     */
    data object Loading : UiState

    /**
     * The state was unable to load.
     */
    data object Failure : UiState

    /**
     * There is a success state, with the given data.
     */
    data object Success : UiState
}