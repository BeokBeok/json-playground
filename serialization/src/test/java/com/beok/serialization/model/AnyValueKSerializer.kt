package com.beok.serialization.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

object AnyValueKSerializer : KSerializer<Any> {
    override val descriptor: SerialDescriptor
        get() = JsonPrimitive.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Any) = Unit

    override fun deserialize(decoder: Decoder): Any {
        fun JsonElement.extractedContent(): Any {
            when(this) {
                is JsonPrimitive -> {
                    if (jsonPrimitive.isString) {
                        return jsonPrimitive.content
                    }
                    return jsonPrimitive.booleanOrNull ?:
                    jsonPrimitive.intOrNull ?:
                    jsonPrimitive.longOrNull ?:
                    jsonPrimitive.floatOrNull ?:
                    jsonPrimitive.doubleOrNull ?:
                    jsonPrimitive.contentOrNull ?:
                    Any()
                }
                is JsonArray -> {
                    return jsonArray.map(JsonElement::extractedContent)
                }
                is JsonObject -> {
                    return jsonObject.entries.associate {
                        it.key to it.value.extractedContent()
                    }
                }
                else -> {
                    return Any()
                }
            }
        }

        return (decoder as? JsonDecoder)?.decodeJsonElement()
            ?.extractedContent()
            ?: Any()
    }
}
