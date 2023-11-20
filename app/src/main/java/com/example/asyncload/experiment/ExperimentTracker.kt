package com.example.asyncload.experiment

import com.example.asyncload.experiment.provider.CustomerUserProvider
import com.example.asyncload.experiment.provider.ExperimentationProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


interface ExperimentTracker {
    val key: String

    suspend fun track()
}

abstract class CustomerExperimentTracker(
    experimentationProvider: ExperimentationProvider,
    customerUserProvider: CustomerUserProvider
) : ExperimentTracker, Awaitable {

    override val isReady: Flow<Boolean> = combine(
        experimentationProvider.isReady,
        customerUserProvider.data
    ) { isExperimentationReady, data ->
        isExperimentationReady && data.isNotEmpty()
    }

    override suspend fun track() {
        isReady.collect { isReady ->
            if (isReady) {
                sendTrackingEvent()
            }
        }
    }

    abstract suspend fun sendTrackingEvent()
}