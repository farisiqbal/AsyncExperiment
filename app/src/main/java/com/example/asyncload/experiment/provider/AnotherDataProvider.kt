package com.example.asyncload.experiment.provider

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnotherDataProvider @Inject constructor() {

    suspend fun fetchData() {
        // we will put this to cache
        delay(3000)
        data.emit("VARIANT2")
    }

    // get from cache
    val data: MutableStateFlow<String> = MutableStateFlow("Loading2...")
}