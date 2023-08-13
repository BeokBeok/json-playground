package com.beok.serialization

import com.beok.serialization.model.DisplayModelSerializer
import com.beok.serialization.model.SerializationResponse
import java.io.File
import kotlin.test.assertEquals
import kotlinx.serialization.json.Json
import org.junit.Test

class SerializationTest {
    @Test
    fun test1() {
        val serialization = Json.decodeFromString<SerializationResponse.First>(
            File("src/test/resources/test.json").readText()
        )

        assertEquals(expected = serialization.a, actual = "a")
        assertEquals(expected = serialization.b, actual = "b")
        assertEquals(expected = serialization.c, actual = "c")
        assertEquals(expected = serialization.e, actual = 0)
        assertEquals(expected = serialization.data.aa.size, actual = 2)
    }

    @Test
    fun test2() {
        val serialization = Json.decodeFromString<SerializationResponse.Second>(
            File("src/test/resources/test1.json").readText()
        )

        assertEquals(expected = serialization.a, actual = "d")
        assertEquals(expected = serialization.b, actual = "e")
        assertEquals(expected = serialization.c, actual = "f")
        assertEquals(expected = serialization.e, actual = 1)
        assertEquals(expected = serialization.data.bb.size, actual = 3)
    }

    @Test
    fun `특정 필드가 없다면 기본값`() {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val serialization = json.decodeFromString<SerializationResponse.First>(
            File("src/test/resources/test2.json").readText()
        )

        assertEquals(expected = serialization.a, actual = "a")
        assertEquals(expected = serialization.b, actual = "")
        assertEquals(expected = serialization.c, actual = "")
        assertEquals(expected = serialization.e, actual = 0)
        assertEquals(expected = serialization.data.aa.size, actual = 2)
    }

    @Test
    fun `Response를 내가 원하는 Model로 변환합니다`() {
        val serialization = Json.decodeFromString(
            deserializer = DisplayModelSerializer(),
            string = File("src/test/resources/test.json").readText()
        )

        assertEquals(expected = 0, actual = serialization.id)
        assertEquals(expected = "b", actual = serialization.description)
        assertEquals(expected = 2, actual = serialization.displays.size)
        assertEquals(expected = "title1", actual = serialization.displays.firstOrNull())
        assertEquals(expected = "title2", actual = serialization.displays.lastOrNull())
    }
}
