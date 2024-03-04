package com.example.currencyconverterapp.core.data.local

import androidx.datastore.core.Serializer
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