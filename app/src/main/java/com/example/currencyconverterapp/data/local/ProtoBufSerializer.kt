package com.example.currencyconverterapp.data.local

import androidx.datastore.core.Serializer
import com.example.currencyconverterapp.data.model.ChartsCachedData
import com.example.currencyconverterapp.data.model.ConverterCachedData
import com.example.currencyconverterapp.data.model.CurrenciesCachedData
import com.example.currencyconverterapp.data.model.WatchlistData
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

open class ProtoBufSerializer<T: Any>(private val dataClass: KClass<T>) : Serializer<T> {

    override val defaultValue: T
        get() = dataClass.createInstance()

    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override suspend fun readFrom(input: InputStream): T {
        return try {
            ProtoBuf.decodeFromByteArray(
                deserializer = dataClass.serializer(),
                bytes = input.readBytes(),
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override suspend fun writeTo(t: T, output: OutputStream) {
        output.write(
            ProtoBuf.encodeToByteArray(
                serializer = dataClass.serializer(),
                value = t
            )
        )
    }
}

object ConverterCachedDataSerializer : ProtoBufSerializer<ConverterCachedData>(ConverterCachedData::class)

object ChartsCachedDataSerializer : ProtoBufSerializer<ChartsCachedData>(ChartsCachedData::class)

object CurrenciesCachedDataSerializer : ProtoBufSerializer<CurrenciesCachedData>(
    CurrenciesCachedData::class)

object WatchlistDataSerializer : ProtoBufSerializer<WatchlistData>(WatchlistData::class)