package com.example.asyncload.experiment

import com.example.asyncload.experiment.provider.CustomerUserProvider
import com.example.asyncload.experiment.provider.ExperimentationProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ExperimentTrackerExample(
    private val experimentationProvider: ExperimentationProvider,
    private val customerUserProvider: CustomerUserProvider
) : CustomerExperimentTracker(experimentationProvider, customerUserProvider){
    override val key: String = "my_tracking_key"

    override val isReady: Flow<Boolean> = combine(
        experimentationProvider.isReady,
        customerUserProvider.data
    ) { isExperimentationReady, data ->
        isExperimentationReady && data.isNotEmpty()
    }

    override suspend fun sendTrackingEvent() {
//        experimentationProvider.track(key, "id", user_attrs = mapOf(), null)
        experimentationProvider.track()
    }


}