package com.beok.serialization

import com.beok.serialization.model.DisplayModelSerializer
import com.beok.serialization.model.DisplayModelSerializer2
import com.beok.serialization.model.SerializationResponse
import com.beok.serialization.model.Time
import java.io.File
import java.util.Date
import kotlin.test.assertEquals
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
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
    fun `long을 Date로 변환합니다`() {
        val serialization = Json.decodeFromString<Time>(
            File("src/test/resources/test3.json").readText()
        )

        assertEquals(expected = Date(1690208913003), actual = serialization.time)
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

    @Test
    fun `Response 모델이 없어도 원하는 Model로 변환합니다`() {
        val serialization = Json.decodeFromString(
            deserializer = DisplayModelSerializer2(),
            string = File("src/test/resources/test.json").readText()
        )

        assertEquals(expected = 0, actual = serialization.id)
        assertEquals(expected = "b", actual = serialization.description)
        assertEquals(expected = 2, actual = serialization.displays.size)
        assertEquals(expected = "title1", actual = serialization.displays.firstOrNull())
        assertEquals(expected = "title2", actual = serialization.displays.lastOrNull())
    }

    @Test
    fun `지수를 Long 타입으로 변환합니다`() {
        val jsonText = """
            {
                "timeSaleEndTime": 1.6974324E12
            }
        """.trimIndent()

        val serialization = Json.decodeFromString<TimeSaleEndTime>(jsonText)

        assertEquals(expected = 1697432400000, actual = serialization.timeSaleEndTime)
    }

    @Serializable
    data class TimeSaleEndTime(
        @Serializable(with = ExponentialAsLongSerializer::class)
        val timeSaleEndTime: Long
    )

    object ExponentialAsLongSerializer : KSerializer<Long> {
        override val descriptor: SerialDescriptor
            get() = JsonPrimitive.serializer().descriptor

        override fun serialize(encoder: Encoder, value: Long) = Unit

        override fun deserialize(decoder: Decoder): Long {
            return decoder.decodeDouble().toLong()
        }
    }
}
