package com.example.asyncload.experiment.provider

import com.example.asyncload.experiment.Awaitable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface ExperimentationProvider : Awaitable {
    // call on app launch
    suspend fun init()

    suspend fun decide(key: String, data: String): String

    suspend fun track(key: String, userId: String, userAttrs: Map<String, Any>)
}

class ExperimentationProviderImpl(
    private val client: ClientSDK
) : ExperimentationProvider {

    override suspend fun init() {
        client.initialize()
        delay(2000)
        isReady.emit(true)
    }

    override suspend fun decide(key: String, data: String): String {
        return client.decide(key, data)
    }

    override suspend fun track(key: String, userId: String, userAttrs: Map<String, Any>) {
        client.track(key, userId, userAttrs)
    }

    override val isReady = MutableStateFlow(false)
}

class ClientSDK {

    fun initialize() {
        // do something
    }

    fun decide(key: String, data: String): String {
        return key
    }

    fun track(key: String, userId: String, userAttrs: Map<String, Any>) {
        // do something
    }
}