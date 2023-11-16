package com.example.asyncload.experiment

import com.example.asyncload.experiment.provider.CustomerUserProvider
import com.example.asyncload.experiment.provider.ExperimentationProvider
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class ExperimentA @Inject constructor(
    private val experimentationProvider: ExperimentationProvider,
    private val customerUserProvider: CustomerUserProvider,
) : CustomerExperiment(experimentationProvider, customerUserProvider) {

    override val key: String = "my_key"

    // this is executed only when it is READY
    override suspend fun FlowCollector<String>.emitVariant() {
        emit(experimentationProvider.decide(key, customerUserProvider.data.value))
    }
}