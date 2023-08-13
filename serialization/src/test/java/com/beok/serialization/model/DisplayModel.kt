package com.beok.serialization.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class DisplayModel(
    val id: Int,
    val description: String,
    val displays: List<String>
)

class DisplayModelSerializer : KSerializer<DisplayModel> {
    override val descriptor: SerialDescriptor
        get() = DisplayModel.serializer().descriptor

    override fun serialize(encoder: Encoder, value: DisplayModel) {
        val display = DisplayModel(
            id = value.id,
            description = value.description,
            displays = value.displays
        )
        encoder.encodeSerializableValue(DisplayModel.serializer(), display)
    }

    override fun deserialize(decoder: Decoder): DisplayModel {
        val first = decoder.decodeSerializableValue(SerializationResponse.First.serializer())
        return DisplayModel(
            id = first.e,
            description = first.b,
            displays = first.data.aa.map(Title::title)
        )
    }
}
