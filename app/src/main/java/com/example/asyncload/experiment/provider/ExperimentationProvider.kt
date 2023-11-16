package com.example.asyncload.experiment.provider

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

interface ExperimentationProvider {
    // call on app launch
    suspend fun init()

    suspend fun decide(key: String, data: String): String

    suspend fun track()

    val isReady: MutableStateFlow<Boolean>
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

    override suspend fun track() { }

    override val isReady = MutableStateFlow(false)
}

class ClientSDK {

    fun initialize() {
        // do something
    }

    fun decide(key: String, data: String): String {
        return data
    }
}