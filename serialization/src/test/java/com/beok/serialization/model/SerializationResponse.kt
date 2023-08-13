package com.beok.serialization.model

import java.util.Date
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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
    data class First constructor(
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
    data class Second constructor(
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

@Serializable
data class Time(
    @Serializable(with = DateAsLongSerializer::class)
    val time: Date
)

object DateAsLongSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Date", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeLong(value.time)
    }
    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeLong())
    }
}
