package com.example.asyncload.experiment

import com.example.asyncload.experiment.provider.CustomerUserProvider
import com.example.asyncload.experiment.provider.ExperimentationProvider
import javax.inject.Inject

class ExperimentTrackerExample @Inject constructor(
    private val experimentationProvider: ExperimentationProvider,
    private val customerUserProvider: CustomerUserProvider
) : CustomerExperimentTracker(experimentationProvider, customerUserProvider) {

    override val key: String = "my_tracking_key"

    override suspend fun sendTrackingEvent() {
        experimentationProvider.track(key, customerUserProvider.data.value, userAttrs = mapOf())
//        experimentationProvider.track()
    }
}