package com.ibenabdallah.motus.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ibenabdallah.motus.data.remote.MotusRemoteRetriever
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
class MotusRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var motusRetriever: MotusRemoteRetriever

    private lateinit var repository: MotusRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = MotusRepositoryImpl(motusRetriever)
    }

    @Test
    fun `invoke should return Data`() = runTest {

        val actualResult = Result.success(response)

        coEvery { motusRetriever.invoke() } returns actualResult

        val result = repository.invoke()

        assertEquals(actualResult, result)
    }

    @Test
    fun `invoke should return empty Data`() = runTest {

        val actualResult = Result.success("")

        coEvery { motusRetriever.invoke() } returns actualResult

        val result = repository.invoke()

        assertEquals(actualResult, result)
    }


    companion object {
        // Sample data
        const val response = """ABACAS
                        ABALES
                        ABAQUE
                        ABASIE
                        ABATEE
                        ABATIS
                        ABATTE
                        ABATTU
                        ABAYAS
                        ABBAYE
                        ABCEDA
                        ABCEDE
                        ABELIA
                        ABELIE
                        ABERRA
                        ABERRE
                        ABETIE
                        ABETIR
                        ABETIS
                        ABETIT
                        ABIMAI
                        ABIMAS
                        ABIMAT
                        ABIMEE
                        ABIMER
                        ABIMES
                        ABIMEZ
                        ABJECT
                        ABJURA
                        ABJURE
                        ABLATA
                        ABLATE
                        ABLIER
                        ABOIES
                        ABOLIE
                        ABOLIR
                        ABOLIS
                        ABOLIT
                        ABONDA
                        ABONDE
                        ABONNA
                        ABONNE
                        ABONNI
                        ABORDA
                        ABORDE
                        """
    }
}

