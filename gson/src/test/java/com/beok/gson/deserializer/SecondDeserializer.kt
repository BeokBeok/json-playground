package com.beok.gson.deserializer

import com.beok.gson.model.SecondData
import com.beok.gson.model.GsonResponse
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object SecondDeserializer : JsonDeserializer<GsonResponse.Second> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GsonResponse.Second {
        return json?.asJsonObject?.run {
            GsonResponse.Second(
                a = get("a")?.asString ?: "",
                b = get("b")?.asString ?: "",
                c = get("c")?.asString ?: "",
                e = get("e")?.asInt ?: -1,
                data = Gson().fromJson(
                    get("data")?.toString() ?: "",
                    object : TypeToken<SecondData>() {}.type
                )
            )
        } ?: GsonResponse.Second()
    }
}
