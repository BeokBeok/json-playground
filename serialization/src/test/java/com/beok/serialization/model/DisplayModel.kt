package com.beok.serialization.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

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

class DisplayModelSerializer2 : KSerializer<DisplayModel> {
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
        require(decoder is JsonDecoder)
        return decoder.decodeJsonElement().jsonObject.let { jsonObject ->
            DisplayModel(
                id = jsonObject["e"]?.jsonPrimitive?.intOrNull ?: -1,
                description = jsonObject["b"]?.jsonPrimitive?.contentOrNull.orEmpty(),
                displays = jsonObject["data"]?.jsonObject?.get("aa")?.jsonArray?.map {
                    it.jsonObject["title"]?.jsonPrimitive?.content.orEmpty()
                }.orEmpty()
            )
        }
    }
}
