package com.xmartlabs.gong.data.repository.store.datastorage

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

/**
 * Created by mirland on 02/10/20.
 */
class JsonDataStoreEntitySerializer<T>(
    private val serializer: KSerializer<T>,
    private val defaultValueCreator: () -> T,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : Serializer<T> {
    override val defaultValue: T
        get() = defaultValueCreator()

    override suspend fun readFrom(input: InputStream): T = withContext(dispatcher) {
        try {
            Json.decodeFromString(
                serializer,
                input.readBytes().decodeToString()
            )
        } catch (serializationException: SerializationException) {
            throw CorruptionException("Unable to deserialize", serializationException)
        }
    }

    override suspend fun writeTo(t: T, output: OutputStream) = withContext(dispatcher) {
        output.write(Json.encodeToString(serializer, t).encodeToByteArray())
    }
}
