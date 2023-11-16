package com.example.asyncload.di

import com.example.asyncload.experiment.provider.ClientSDK
import com.example.asyncload.experiment.provider.ExperimentationProvider
import com.example.asyncload.experiment.provider.ExperimentationProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesExperimentationProvider(): ExperimentationProvider {
        return ExperimentationProviderImpl(ClientSDK())
    }
}