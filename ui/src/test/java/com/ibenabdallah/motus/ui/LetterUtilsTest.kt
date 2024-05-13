package com.ibenabdallah.motus.ui

import com.ibenabdallah.motus.domain.model.Letter
import com.ibenabdallah.motus.domain.model.Status
import org.junit.Assert.assertEquals
import org.junit.Test


class LetterUtilsTest {

    @Test
    fun `call validate should return true`() {
        val current = listOf(
            Letter('A', Status.None),
            Letter('B', Status.None),
            Letter('A', Status.None),
            Letter('C', Status.None),
            Letter('A', Status.None),
            Letter('S', Status.None)
        )
        val essay = listOf(
            Letter('A', Status.None),
            Letter('B', Status.None),
            Letter('A', Status.None),
            Letter('L', Status.None),
            Letter('E', Status.None),
            Letter('S', Status.None)
        )
        val expected = listOf(
            Letter('A', Status.Correct),
            Letter('B', Status.Correct),
            Letter('A', Status.Correct),
            Letter('L', Status.Wrong),
            Letter('E', Status.Wrong),
            Letter('S', Status.Correct)
        )

        val result = validate(current.toMutableList(), essay.toMutableList())

        assertEquals(expected, result)
    }

    @Test
    fun `call validate should return false`() {
        val current = listOf(
            Letter('C', Status.None),
            Letter('O', Status.None),
            Letter('T', Status.None),
            Letter('I', Status.None),
            Letter('C', Status.None),
            Letter('E', Status.None)
        )
        val essay = listOf(
            Letter('C', Status.None),
            Letter('O', Status.None),
            Letter('I', Status.None),
            Letter('E', Status.None),
            Letter('C', Status.None),
            Letter('I', Status.None)
        )
        val expected = listOf(
            Letter('C', Status.Correct),
            Letter('O', Status.Correct),
            Letter('I', Status.WrongPlace),
            Letter('E', Status.WrongPlace),
            Letter('C', Status.Correct),
            Letter('I', Status.Wrong)
        )

        val result = validate(current.toMutableList(), essay.toMutableList())

        assertEquals(expected, result)
    }


    @Test
    fun `call isCorrect should return true`() {

        val essay = listOf(
            Letter('A', Status.Correct),
            Letter('B', Status.Correct),
            Letter('A', Status.Correct),
            Letter('L', Status.Correct),
            Letter('E', Status.Correct),
            Letter('S', Status.Correct)
        )

        val result = essay.isCorrect()

        assertEquals(true, result)
    }

    @Test
    fun `call isCorrect should return false`() {

        val essay = listOf(
            Letter('A', Status.Correct),
            Letter('B', Status.Correct),
            Letter('A', Status.Correct),
            Letter('L', Status.Correct),
            Letter('E', Status.Correct),
            Letter('S', Status.Wrong)
        )

        val result = essay.isCorrect()

        assertEquals(false, result)
    }

    @Test
    fun `call parseStringToLetter should return List Letter`() {

        val essay = "ABALES"
        val expected = listOf(
            Letter('A', Status.None),
            Letter('B', Status.None),
            Letter('A', Status.None),
            Letter('L', Status.None),
            Letter('E', Status.None),
            Letter('S', Status.None)
        )

        val result = parseStringToLetter(essay)

        assertEquals(expected, result)
    }
}