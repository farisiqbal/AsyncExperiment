package com.example.asyncload.experiment.provider

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerUserProvider @Inject constructor() {

    suspend fun fetchData() {
        // we will put this to cache
        delay(4000)
        data.emit("VARIANT")
    }

    // get from cache
    val data: MutableStateFlow<String> = MutableStateFlow("")
}