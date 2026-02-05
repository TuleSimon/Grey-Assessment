package com.simon.greyassesment.di

import com.simon.greyassesment.data.repository.LearningRepositoryImpl
import com.simon.greyassesment.domain.repository.LearningRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLearningRepository(): LearningRepository {
        return LearningRepositoryImpl()
    }
}