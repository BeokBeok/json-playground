package com.beok.serialization.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class SerializationResponse constructor(
    @Transient
    open val a: String = "",
    @Transient
    open val b: String = "",
    @Transient
    open val c: String = "",
    @Transient
    open val e: Int = -1,
) {
    @Serializable
    data class First(
        override val a: String,
        override val b: String = "",
        override val c: String = "",
        override val e: Int,
        val data: FirstData = FirstData()
    ) : SerializationResponse(
        a = a,
        b = b,
        c = c,
        e = e
    )

    @Serializable
    data class Second(
        override val a: String,
        override val b: String,
        override val c: String,
        override val e: Int,
        val data: SecondData = SecondData()
    ) : SerializationResponse(
        a = a,
        b = b,
        c = c,
        e = e
    )
}

@Serializable
data class FirstData(
    val aa: List<Title> = emptyList()
)

@Serializable
data class Title(
    val title: String
)

@Serializable
data class SecondData(
    val bb: List<Count> = emptyList()
)

@Serializable
data class Count(
    val count: Int
)
