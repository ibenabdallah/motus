package com.ibenabdallah.motus.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ibenabdallah.motus.domain.model.Letter
import com.ibenabdallah.motus.domain.model.Status
import com.ibenabdallah.motus.domain.repository.MotusRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAllMotusUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: MotusRepository

    private lateinit var useCase: GetAllMotusUseCase


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetAllMotusUseCaseImpl(repository)
    }

    @Test
    fun `invoke should return success Data`() = runTest {

        val expectedResult = Result.success(responseLetter)

        coEvery { repository.invoke() } returns Result.success(response)

        val data: Result<List<List<Letter>>> = useCase.invoke()

        assertEquals(expectedResult, data)
    }

    @Test
    fun `invoke should return success empty Data`() = runTest {

        val expectedResult = Result.success<List<List<Letter>>>(listOf())

        coEvery { repository.invoke() } returns Result.success("")

        val data : Result<List<List<Letter>>> = useCase.invoke()

        assertEquals(expectedResult, data)
    }

    @Test
    fun `invoke should return success empty Data 2`() = runTest {

        val expectedResult = Result.success<List<List<Letter>>>(listOf())

        coEvery { repository.invoke() } returns Result.success("\n")

        val data = useCase.invoke()

        assertEquals(expectedResult, data)
    }


    @Test
    fun `invoke should return failure`() = runTest {

        val throwable = Throwable()
        val expectedResult = Result.failure<List<List<Letter>>>(throwable)

        coEvery { repository.invoke() } returns Result.failure(throwable)

        val data : Result<List<List<Letter>>> = useCase.invoke()

        assertEquals(expectedResult, data)
    }


    companion object {
        const val response = """ABACAS
            ABALES
            ABAQUE
            ABASIE
            ABATEE
            ABATIS
            ABATTE
            ABATTU
            ABAYAS"""

        val responseLetter = listOf(
            listOf(Letter('A', Status.None), Letter('B',Status.None), Letter('A',Status.None), Letter('C',Status.None), Letter('A',Status.None), Letter('S',Status.None)),
            listOf(Letter('A', Status.None), Letter('B',Status.None), Letter('A',Status.None), Letter('L',Status.None), Letter('E',Status.None), Letter('S',Status.None)),
            listOf(Letter('A', Status.None), Letter('B',Status.None), Letter('A',Status.None), Letter('Q',Status.None), Letter('U',Status.None), Letter('E',Status.None)),
            listOf(Letter('A', Status.None), Letter('B',Status.None), Letter('A',Status.None), Letter('S',Status.None), Letter('I',Status.None), Letter('E',Status.None)),
            listOf(Letter('A', Status.None), Letter('B',Status.None), Letter('A',Status.None), Letter('T',Status.None), Letter('E',Status.None), Letter('E',Status.None)),
            listOf(Letter('A', Status.None), Letter('B',Status.None), Letter('A',Status.None), Letter('T',Status.None), Letter('I',Status.None), Letter('S',Status.None)),
            listOf(Letter('A', Status.None), Letter('B',Status.None), Letter('A',Status.None), Letter('T',Status.None), Letter('T',Status.None), Letter('E',Status.None)),
            listOf(Letter('A', Status.None), Letter('B',Status.None), Letter('A',Status.None), Letter('T',Status.None), Letter('T',Status.None), Letter('U',Status.None)),
            listOf(Letter('A', Status.None), Letter('B',Status.None), Letter('A',Status.None), Letter('Y',Status.None), Letter('A',Status.None), Letter('S',Status.None)),
        )

    }
}

