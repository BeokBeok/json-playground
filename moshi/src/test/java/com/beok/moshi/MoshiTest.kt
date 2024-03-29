package com.beok.moshi

import com.beok.moshi.model.DisplayModel
import com.beok.moshi.model.DisplayModelAdapter
import com.beok.moshi.model.MoshiResponse
import com.beok.moshi.model.Time
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.util.Date
import kotlin.test.assertEquals
import org.junit.Test

class MoshiTest {
    @Test
    fun test1() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(MoshiResponse.First::class.java)
            .fromJson(File("src/test/resources/test.json").readText())

        assertEquals(expected = moshi?.a, actual = "a")
        assertEquals(expected = moshi?.b, actual = "b")
        assertEquals(expected = moshi?.c, actual = "c")
        assertEquals(expected = moshi?.e, actual = 0)
        assertEquals(expected = moshi?.data?.aa?.size, actual = 2)
    }

    @Test
    fun test2() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(MoshiResponse.Second::class.java)
            .fromJson(File("src/test/resources/test1.json").readText())

        assertEquals(expected = moshi?.a, actual = "d")
        assertEquals(expected = moshi?.b, actual = "e")
        assertEquals(expected = moshi?.c, actual = "f")
        assertEquals(expected = moshi?.e, actual = 1)
        assertEquals(expected = moshi?.data?.bb?.size, actual = 3)
    }

    @Test
    fun `특정 필드가 없다면 기본값`() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(MoshiResponse.First::class.java)
            .fromJson(File("src/test/resources/test2.json").readText())

        assertEquals(expected = moshi?.a, actual = "a")
        assertEquals(expected = moshi?.b, actual = "")
        assertEquals(expected = moshi?.c, actual = "")
        assertEquals(expected = moshi?.e, actual = 0)
        assertEquals(expected = moshi?.data?.aa?.size, actual = 2)
    }

    @Test
    fun jodaTime() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()
            .adapter(Time::class.java)
            .fromJson(File("src/test/resources/test3.json").readText())

        assertEquals(expected = 1690208913003, actual = moshi?.time?.time)
    }

    @Test
    fun `Response를 내가 원하는 Model로 변환합니다`() {
        val moshi = Moshi.Builder()
            .add(DisplayModelAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(DisplayModel::class.java)
            .fromJson(File("src/test/resources/test.json").readText())

        assertEquals(expected = 0, actual = moshi?.id)
        assertEquals(expected = "b", actual = moshi?.description)
        assertEquals(expected = 2, actual = moshi?.displays?.size)
        assertEquals(expected = "title1", actual = moshi?.displays?.firstOrNull())
        assertEquals(expected = "title2", actual = moshi?.displays?.lastOrNull())
    }
}
