package com.singhtwenty2.konix.feature_portfolio.di

import android.content.SharedPreferences
import com.singhtwenty2.konix.feature_portfolio.data.remote.PortfolioRemoteService
import com.singhtwenty2.konix.feature_portfolio.data.repository.PortfolioRepositoryImpl
import com.singhtwenty2.konix.feature_portfolio.domain.repository.PortFolioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PortfolioModule {

    @Provides
    @Singleton
    fun providesPortfolioRemoteService(): PortfolioRemoteService {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.168:7777/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PortfolioRemoteService::class.java)
    }

    @Provides
    @Singleton
    fun providesPortfolioRepository(
        portfolioRemoteService: PortfolioRemoteService,
        sharedPreferences: SharedPreferences
    ): PortFolioRepository {
        return PortfolioRepositoryImpl(
            portfolioRemoteService,
            sharedPreferences
        )
    }
}