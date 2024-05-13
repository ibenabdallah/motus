package com.ibenabdallah.motus.ui

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibenabdallah.motus.domain.GetAllMotusUseCase
import com.ibenabdallah.motus.domain.model.Letter
import com.ibenabdallah.motus.ui.di.ViewModelCoroutineContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

const val NBR_ESSAY_MAX = 7

@HiltViewModel
class MotusViewModel @Inject constructor(
    private val useCase: GetAllMotusUseCase,
    @ViewModelCoroutineContext private val dispatcher: CoroutineContext,
) : ViewModel() {

    private val _jeuState = MutableStateFlow<JeuState?>(null)
    val jeuState = _jeuState

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val _allWord = MutableStateFlow<List<List<Letter>>>(emptyList())

    private val _currentRandom = MutableStateFlow<List<Letter>?>(null)

    private val _currentEssays = MutableStateFlow<MutableList<List<Letter>>>(mutableListOf())
    val currentEssays = _currentEssays

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val _nbrEssays = MutableStateFlow(0)

    init {
        viewModelScope.launch(dispatcher) {
            _allWord.collect {
                findRandomWord(it)
            }
        }
    }

    fun fetch() {
        viewModelScope.launch(dispatcher) {
            val data = useCase.invoke()
            if (data.isSuccess) {
                val words = data.getOrNull()
                _state.value = if (words.isNullOrEmpty()) {
                    UiState.Failure
                } else {
                    _allWord.value = words
                    UiState.Success
                }
            } else {
                _state.value = UiState.Failure
            }
        }
    }

    fun reset() {
        _score.value = 0
        _nbrEssays.value = 0
        _jeuState.value = null
        _currentEssays.value.clear()
        findRandomWord(_allWord.value)
    }

    fun next() {
        _nbrEssays.value = 0
        _jeuState.value = null
        _currentEssays.value.clear()
        findRandomWord(_allWord.value)
    }

    private fun findRandomWord(data: List<List<Letter>>) {
        if (data.isNotEmpty()) {
            val indexRandom = Random.nextInt(data.size)
            val randomWord = data[indexRandom]
            _currentRandom.value = randomWord
            _currentEssays.value = mutableListOf(randomWord)
        }
    }


    fun validate(motEssay: String) {
        _currentRandom.value?.let { current ->
            _nbrEssays.value++
            val essay = validate(current, parseStringToLetter(motEssay))

            val currentEssays = _currentEssays.value.toMutableList()

            when {
                essay.isCorrect() -> {
                    _score.value++
                    _jeuState.value = JeuState.Success
                    currentEssays.removeLast()
                    currentEssays.add(essay)
                }

                _nbrEssays.value == NBR_ESSAY_MAX -> {
                    _jeuState.value = JeuState.Failure
                    currentEssays.removeLast()
                    currentEssays.add(essay)
                }

                else -> currentEssays.add(currentEssays.size - 1, essay)
            }

            _currentEssays.value = currentEssays
        }
    }
}