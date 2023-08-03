package com.beok.moshi.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

data class DisplayModel(
    val id: Int,
    val description: String,
    val displays: List<String>
)

class DisplayModelAdapter : JsonAdapter<DisplayModel>() {
    @FromJson
    override fun fromJson(reader: JsonReader): DisplayModel? {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(MoshiResponse.First::class.java)
        return moshi.fromJson(reader)?.let {
            DisplayModel(
                id = it.e,
                description = it.b,
                displays = it.data.aa.map(Title::title)
            )
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: DisplayModel?) {
        writer.jsonValue(value)
    }
}
