package com.beok.gson

import com.beok.gson.model.GsonResponse
import com.google.gson.Gson
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class GsonTest {
    @Test
    fun test1() {
        val gson = Gson().fromJson(
            File("src/test/resources/test.json").readText(),
            GsonResponse.First::class.java
        )

        assertEquals(expected = gson.a, actual = "a")
        assertEquals(expected = gson.b, actual = "b")
        assertEquals(expected = gson.c, actual = "c")
        assertEquals(expected = gson.e, actual = 0)
        assertEquals(expected = gson.data.aa.size, actual = 2)
    }

    @Test
    fun test2() {
        val gson = Gson().fromJson(
            File("src/test/resources/test1.json").readText(),
            GsonResponse.Second::class.java
        )

        assertEquals(expected = gson.a, actual = "d")
        assertEquals(expected = gson.b, actual = "e")
        assertEquals(expected = gson.c, actual = "f")
        assertEquals(expected = gson.e, actual = 1)
        assertEquals(expected = gson.data.bb.size, actual = 3)
    }

    @Test
    fun `특정 필드가 없다면 기본값`() {
        val gson = Gson().fromJson(
            File("src/test/resources/test2.json").readText(),
            GsonResponse.First::class.java
        )

        assertEquals(expected = gson.a, actual = "a")
        assertEquals(expected = gson.b, actual = "")
        assertEquals(expected = gson.c, actual = "")
        assertEquals(expected = gson.e, actual = 0)
        assertEquals(expected = gson.data.aa.size, actual = 2)
    }
}
