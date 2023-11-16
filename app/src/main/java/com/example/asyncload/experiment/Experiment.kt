package com.example.asyncload.experiment

import com.example.asyncload.experiment.provider.CustomerUserProvider
import com.example.asyncload.experiment.provider.ExperimentationProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

interface Experiment {
    val key: String

    val isReady: Flow<Boolean>

    fun decide() = flow {
        isReady.collect { isReady ->
            if (isReady) {
                emitVariant()
            }
        }
    }

    suspend fun FlowCollector<String>.emitVariant()
}

abstract class CustomerExperiment(
    experimentationProvider: ExperimentationProvider,
    customerUserProvider: CustomerUserProvider
) : Experiment {

    override val isReady: Flow<Boolean> = combine(
        experimentationProvider.isReady,
        customerUserProvider.data
    ) { isExperimentationReady, data ->
        isExperimentationReady && data.isNotEmpty()
    }
}

abstract class PublicExperiment(
    experimentationProvider: ExperimentationProvider
) : Experiment {

    override val isReady: Flow<Boolean> = experimentationProvider.isReady
}