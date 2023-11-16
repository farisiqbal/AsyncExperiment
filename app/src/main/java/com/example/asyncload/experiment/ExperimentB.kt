package com.example.asyncload.experiment

import com.example.asyncload.experiment.provider.CustomerUserProvider
import com.example.asyncload.experiment.provider.ExperimentationProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// If we don't use abstraction
class ExperimentB @Inject constructor(
    private val experimentationProvider: ExperimentationProvider,
    private val customerUserProvider: CustomerUserProvider
) {

    private val isReady: Flow<Boolean> = combine(
        experimentationProvider.isReady,
        customerUserProvider.data
    ) { isExperimentationReady, data ->
        isExperimentationReady && data.isNotEmpty()
    }

    fun decide(): Flow<String> = flow {
        isReady.collect { isReady ->
            if (isReady) {
                emit(
                    experimentationProvider.decide("my_other_key", customerUserProvider.data.value)
                )
            }
        }
    }
}