package com.singhtwenty2.konix.feature_order_placing.di

import android.content.SharedPreferences
import com.singhtwenty2.konix.feature_order_placing.data.remote.OrderRemoteService
import com.singhtwenty2.konix.feature_order_placing.data.repository.OrderRepositoryImpl
import com.singhtwenty2.konix.feature_order_placing.domain.repository.OrderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderModule {

    @Provides
    @Singleton
    fun providesOrderRemoteService(): OrderRemoteService {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.168:7777/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(OrderRemoteService::class.java)
    }

    @Provides
    @Singleton
    fun providesOrderRepository(
        orderRemoteService: OrderRemoteService,
        sharedPreferences: SharedPreferences
    ): OrderRepository {
        return OrderRepositoryImpl(orderRemoteService, sharedPreferences)
    }
}