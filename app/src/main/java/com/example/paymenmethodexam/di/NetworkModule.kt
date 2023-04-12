package com.example.paymenmethodexam.di

import android.content.Context
import com.example.paymenmethodexam.data.network.RemoteDataSource
import com.example.paymenmethodexam.data.remote.PaymentService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideCreateAuthService(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): PaymentService {
        return remoteDataSource.buildPaymentApi(PaymentService::class.java, context)
    }

}