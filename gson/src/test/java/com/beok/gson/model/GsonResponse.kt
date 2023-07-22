package com.beok.gson.model

import com.beok.gson.deserializer.FirstDeserializer
import com.beok.gson.deserializer.SecondDeserializer
import com.google.gson.annotations.JsonAdapter

sealed class GsonResponse constructor(
    open val a: String,
    open val b: String,
    open val c: String,
    open val e: Int
) {
    @JsonAdapter(FirstDeserializer::class)
    data class First constructor(
        override val a: String = "",
        override val b: String = "",
        override val c: String = "",
        override val e: Int = -1,
        val data: FirstData = FirstData()
    ) : GsonResponse(
        a = a,
        b = b,
        c = c,
        e = e
    )

    @JsonAdapter(SecondDeserializer::class)
    data class Second constructor(
        override val a: String = "",
        override val b: String = "",
        override val c: String = "",
        override val e: Int = -1,
        val data: SecondData = SecondData()
    ) : GsonResponse(
        a = a,
        b = b,
        c = c,
        e = e
    )
}

data class FirstData(
    val aa: List<Title> = emptyList()
)

data class Title(
    val title: String
)

data class SecondData(
    val bb: List<Count> = emptyList()
)

data class Count(
    val count: Int
)
