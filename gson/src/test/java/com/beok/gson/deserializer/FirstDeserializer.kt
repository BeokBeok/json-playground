package com.beok.gson.deserializer

import com.beok.gson.model.FirstData
import com.beok.gson.model.GsonResponse
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object FirstDeserializer : JsonDeserializer<GsonResponse.First> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GsonResponse.First {
        return json?.asJsonObject?.run {
            GsonResponse.First(
                a = get("a")?.asString ?: "",
                b = get("b")?.asString ?: "",
                c = get("c")?.asString ?: "",
                e = get("e")?.asInt ?: -1,
                data = Gson().fromJson(
                    get("data")?.toString() ?: "",
                    object : TypeToken<FirstData>() {}.type
                )
            )
        } ?: GsonResponse.First()
    }
}
