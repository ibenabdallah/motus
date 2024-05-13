package com.ibenabdallah.motus.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ibenabdallah.motus.data.network.MotusApi
import com.ibenabdallah.motus.data.remote.MotusRemoteRetriever
import com.ibenabdallah.motus.data.remote.MotusRemoteRetrieverImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class MotusRemoteRetrieverTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var api: MotusApi

    private lateinit var motusRetriever: MotusRemoteRetriever

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        motusRetriever = MotusRemoteRetrieverImpl(api, testDispatcher)
    }

    @Test
    fun `WCs paging source load - failure - http error`() = runTest(testDispatcher) {

        val error = Response.error<String>(404, "null".toResponseBody())

        coEvery { api.getAll() } returns error

        val expectedResult = Result.failure<String>(exception = Throwable(error.message()))

        val actualResult = motusRetriever.invoke()

        assertEquals(
            expectedResult.toString(),
            actualResult.toString()
        )
    }


    @Test
    fun `Get all data - success`() = runTest(testDispatcher) {

        coEvery { api.getAll() } returns Response.success(response)

        val expectedResult = Result.success(response)

        assertEquals(
            expectedResult,
            motusRetriever.invoke()
        )
    }

    @Test
    fun `Get empty data - success`() = runTest(testDispatcher) {

        coEvery { api.getAll() } returns Response.success("")

        val expectedResult = Result.success("")

        assertEquals(
            expectedResult,
            motusRetriever.invoke()
        )
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