package com.beok.moshi.model

import com.squareup.moshi.JsonClass
import java.util.Date

sealed class MoshiResponse constructor(
    open val a: String,
    open val b: String,
    open val c: String,
    open val e: Int
) {
    @JsonClass(generateAdapter = true)
    data class First constructor(
        override val a: String = "",
        override val b: String = "",
        override val c: String = "",
        override val e: Int = -1,
        val data: FirstData = FirstData()
    ) : MoshiResponse(
        a = a,
        b = b,
        c = c,
        e = e
    )

    @JsonClass(generateAdapter = true)
    data class Second constructor(
        override val a: String = "",
        override val b: String = "",
        override val c: String = "",
        override val e: Int = -1,
        val data: SecondData = SecondData()
    ) : MoshiResponse(
        a = a,
        b = b,
        c = c,
        e = e
    )
}

@JsonClass(generateAdapter = true)
data class FirstData(
    val aa: List<Title> = emptyList()
)

data class Title(
    val title: String
)

@JsonClass(generateAdapter = true)
data class SecondData(
    val bb: List<Count> = emptyList()
)

data class Count(
    val count: Int
)

data class Time(
    val time: Date
)
