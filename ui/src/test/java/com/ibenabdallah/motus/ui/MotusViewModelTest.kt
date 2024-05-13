package com.ibenabdallah.motus.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ibenabdallah.motus.domain.GetAllMotusUseCase
import com.ibenabdallah.motus.domain.model.Letter
import com.ibenabdallah.motus.domain.model.Status
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MotusViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var useCase: GetAllMotusUseCase

    private lateinit var viewModel: MotusViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `call fetch should return Success`() = runTest {
        viewModel = MotusViewModel(useCase, testDispatcher)

        // Mocking the data from use case
        val letters = listOf(parseLetter(LETTER_ABACAS, Status.None))
        val data = Result.success(letters)

        coEvery { useCase.invoke() } returns data

        // Calling fetch to populate _allWord
        viewModel.fetch()

        // Asserting the state
        assertEquals(UiState.Success, viewModel.state.value)

        // Asserting the _allWord
        assertEquals(letters, viewModel._allWord.value)
    }

    @Test
    fun `call fetch should return Failure with failure`() = runTest {
        viewModel = MotusViewModel(useCase, testDispatcher)

        // Mocking the data from use case
        val data = Result.failure<List<List<Letter>>>(Throwable())

        coEvery { useCase.invoke() } returns data

        // Calling fetch to populate _allWord
        viewModel.fetch()

        // Asserting the state
        assertEquals(UiState.Failure, viewModel.state.value)

        // Asserting the _allWord
        assertEquals(listOf<List<List<Letter>>>(), viewModel._allWord.value)
    }

    @Test
    fun `call fetch should return Failure with empty`() = runTest {
        viewModel = MotusViewModel(useCase, testDispatcher)

        // Mocking the data from use case
        val data = Result.success<List<List<Letter>>>(listOf())

        coEvery { useCase.invoke() } returns data

        // Calling fetch to populate _allWord
        viewModel.fetch()

        // Asserting the state
        assertEquals(UiState.Failure, viewModel.state.value)

        // Asserting the _allWord
        assertEquals(listOf<List<List<Letter>>>(), viewModel._allWord.value)
    }

    @Test
    fun `call validate should return Success essay`() = runTest {

        viewModel = MotusViewModel(useCase, testDispatcher)

        // Mocking the data from use case
        val letter = parseLetter(LETTER_ABACAS, Status.None)
        val data = Result.success(listOf(letter))

        coEvery { useCase.invoke() } returns data

        // Calling fetch to populate _allWord
        viewModel.fetch()

        // Calling validate
        viewModel.validate("ABACAS")

        // Asserting the jeuState
        assertEquals(JeuState.Success, viewModel.jeuState.value)

        // Asserting the score
        assertEquals(1, viewModel.score.value)

        // Asserting the currentEssays
        val expected = listOf(parseLetter(LETTER_ABACAS, Status.Correct))
        assertEquals(expected, viewModel.currentEssays.value)
    }

    @Test
    fun `call validate should return failure essay cas_1`() = runTest {
        viewModel = MotusViewModel(useCase, testDispatcher)

        // Mocking the data from use case
        val letter = parseLetter(LETTER_ABACAS, Status.None)
        val data = Result.success(listOf(letter))

        coEvery { useCase.invoke() } returns data

        // Calling fetch to populate _allWord
        viewModel.fetch()

        // Calling validate
        viewModel.validate("ABACAX")

        // Asserting the jeuState
        assertEquals(null, viewModel.jeuState.value)

        // Asserting the score
        assertEquals(0, viewModel.score.value)

        // Asserting the currentEssays
        assertEquals(
            listOf(
                listOf(
                    Letter('A', Status.Correct),
                    Letter('B', Status.Correct),
                    Letter('A', Status.Correct),
                    Letter('C', Status.Correct),
                    Letter('A', Status.Correct),
                    Letter('X', Status.Wrong)
                ),
                letter
            ),
            viewModel.currentEssays.value
        )
    }

    @Test
    fun `call validate should return failure essay cas_2`() = runTest {
        viewModel = MotusViewModel(useCase, testDispatcher)

        // Mocking the data from use case
        val letter = parseLetter(LETTER_ABACAS, Status.None)
        val data = Result.success(listOf(letter))

        coEvery { useCase.invoke() } returns data

        // Calling fetch to populate _allWord
        viewModel.fetch()

        // Calling validate
        viewModel.validate("ABLAIO")

        // Asserting the jeuState
        assertEquals(null, viewModel.jeuState.value)

        // Asserting the score
        assertEquals(0, viewModel.score.value)

        // Asserting the currentEssays
        assertEquals(
            listOf(
                listOf(
                    Letter('A', Status.Correct),
                    Letter('B', Status.Correct),
                    Letter('L', Status.Wrong),
                    Letter('A', Status.WrongPlace),
                    Letter('I', Status.Wrong),
                    Letter('O', Status.Wrong)
                ),
                letter
            ),
            viewModel.currentEssays.value
        )
    }

    @Test
    fun `call validate should return failure essay - attends 7 essays`() = runTest {
        viewModel = MotusViewModel(useCase, testDispatcher)

        // Mocking the data from use case
        val letter = parseLetter(LETTER_ABACAS, Status.None)
        val data = Result.success(listOf(letter))

        coEvery { useCase.invoke() } returns data

        // Calling fetch to populate _allWord
        viewModel.fetch()

        // Calling validate
        viewModel.validate("ABACAX")
        viewModel.validate("ABACAU")
        viewModel.validate("ABACAI")
        viewModel.validate("ABACAO")
        viewModel.validate("ABACAM")
        viewModel.validate("ABACAN")
        viewModel.validate("ABACAL")

        // Asserting the jeuState
        assertEquals(JeuState.Failure, viewModel.jeuState.value)

        // Asserting the score
        assertEquals(0, viewModel.score.value)

        // Asserting the currentEssays
        assertEquals(letterWrong, viewModel.currentEssays.value)
    }

    @Test
    fun `call next should continue jeu`() = runTest {

        viewModel = MotusViewModel(useCase, testDispatcher)

        // Mocking the data from use case
        val letters = listOf(parseLetter(LETTER_ABACAS, Status.None))
        val data = Result.success(letters)

        coEvery { useCase.invoke() } returns data

        // Calling fetch to populate _allWord
        viewModel.fetch()

        // Calling validate
        viewModel.validate("ABACAS")

        // Asserting the jeuState
        assertEquals(JeuState.Success, viewModel.jeuState.value)

        // Asserting the score
        assertEquals(1, viewModel.score.value)

        // Asserting the currentEssays
        val expected1 = listOf(parseLetter(LETTER_ABACAS, Status.Correct))
        assertEquals(expected1, viewModel.currentEssays.value)


        // Calling next
        viewModel.next()

        // Asserting the score
        assertEquals(1, viewModel.score.value)

        // Asserting the jeuState
        assertEquals(null, viewModel.jeuState.value)

        // Asserting the currentEssays
        assertEquals(letters, viewModel.currentEssays.value)

        assertEquals(0, viewModel._nbrEssays.value)

        // Calling validate
        viewModel.validate("ABACAS")

        // Asserting the jeuState
        assertEquals(JeuState.Success, viewModel.jeuState.value)

        // Asserting the score
        assertEquals(2, viewModel.score.value)

        // Asserting the currentEssays
        val expected2 = listOf(parseLetter(LETTER_ABACAS, Status.Correct))
        assertEquals(expected2, viewModel.currentEssays.value)
    }

    @Test
    fun `call reset should restart jeu`() = runTest {

        viewModel = MotusViewModel(useCase, testDispatcher)

        // Mocking the data from use case
        val letters = listOf(parseLetter(LETTER_ABACAS, Status.None))
        val data = Result.success(letters)

        coEvery { useCase.invoke() } returns data

        // Calling fetch to populate _allWord
        viewModel.fetch()

        // Calling validate
        viewModel.validate("ABACAX")
        viewModel.validate("ABACAU")
        viewModel.validate("ABACAI")
        viewModel.validate("ABACAO")
        viewModel.validate("ABACAM")
        viewModel.validate("ABACAN")
        viewModel.validate("ABACAL")


        // Asserting the jeuState
        assertEquals(JeuState.Failure, viewModel.jeuState.value)

        // Asserting the score
        assertEquals(0, viewModel.score.value)

        // Asserting the currentEssays
        assertEquals(letterWrong, viewModel.currentEssays.value)


        // Calling next
        viewModel.reset()

        // Asserting the score
        assertEquals(0, viewModel.score.value)

        // Asserting the jeuState
        assertEquals(null, viewModel.jeuState.value)

        // Asserting the currentEssays
        assertEquals(letters, viewModel.currentEssays.value)

        assertEquals(0, viewModel._nbrEssays.value)

        // Calling validate
        viewModel.validate("ABACAS")

        // Asserting the jeuState
        assertEquals(JeuState.Success, viewModel.jeuState.value)

        // Asserting the score
        assertEquals(1, viewModel.score.value)

        // Asserting the currentEssays
        val expected1 = listOf(parseLetter(LETTER_ABACAS, Status.Correct))
        assertEquals(expected1, viewModel.currentEssays.value)
    }

    companion object {

        fun parseLetter(letter: String, status: Status) = letter.map { Letter(it, status) }

        const val LETTER_ABACAS = "ABACAS"

        val letterWrong = listOf(
            listOf(
                Letter('A', Status.Correct),
                Letter('B', Status.Correct),
                Letter('A', Status.Correct),
                Letter('C', Status.Correct),
                Letter('A', Status.Correct),
                Letter('X', Status.Wrong)
            ),
            listOf(
                Letter('A', Status.Correct),
                Letter('B', Status.Correct),
                Letter('A', Status.Correct),
                Letter('C', Status.Correct),
                Letter('A', Status.Correct),
                Letter('U', Status.Wrong)
            ),
            listOf(
                Letter('A', Status.Correct),
                Letter('B', Status.Correct),
                Letter('A', Status.Correct),
                Letter('C', Status.Correct),
                Letter('A', Status.Correct),
                Letter('I', Status.Wrong)
            ),
            listOf(
                Letter('A', Status.Correct),
                Letter('B', Status.Correct),
                Letter('A', Status.Correct),
                Letter('C', Status.Correct),
                Letter('A', Status.Correct),
                Letter('O', Status.Wrong)
            ),
            listOf(
                Letter('A', Status.Correct),
                Letter('B', Status.Correct),
                Letter('A', Status.Correct),
                Letter('C', Status.Correct),
                Letter('A', Status.Correct),
                Letter('M', Status.Wrong)
            ),
            listOf(
                Letter('A', Status.Correct),
                Letter('B', Status.Correct),
                Letter('A', Status.Correct),
                Letter('C', Status.Correct),
                Letter('A', Status.Correct),
                Letter('N', Status.Wrong)
            ),
            listOf(
                Letter('A', Status.Correct),
                Letter('B', Status.Correct),
                Letter('A', Status.Correct),
                Letter('C', Status.Correct),
                Letter('A', Status.Correct),
                Letter('L', Status.Wrong)
            )
        )
    }

}