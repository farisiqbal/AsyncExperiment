package com.example.asyncload.experiment

import com.example.asyncload.experiment.provider.CustomerUserProvider
import com.example.asyncload.experiment.provider.ExperimentationProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

interface Experiment {
    val key: String

    fun decide(): Flow<String>
}

abstract class BaseExperiment(
    experimentationProvider: ExperimentationProvider
) : Experiment, Awaitable {

    override val isReady: Flow<Boolean> = experimentationProvider.isReady

    override fun decide() = flow {
        isReady.collect { isReady ->
            if (isReady) {
                makeDecision()
            }
        }
    }

    abstract suspend fun FlowCollector<String>.makeDecision()
}

abstract class CustomerExperiment(
    experimentationProvider: ExperimentationProvider,
    customerUserProvider: CustomerUserProvider
) : BaseExperiment(experimentationProvider) {

    override val isReady: Flow<Boolean> = combine(
        experimentationProvider.isReady,
        customerUserProvider.data
    ) { isExperimentationReady, data ->
        isExperimentationReady && data.isNotEmpty()
    }
}

interface Awaitable {

    val isReady: Flow<Boolean>
}
