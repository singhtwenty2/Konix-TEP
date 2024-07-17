package com.singhtwenty2.konix.feature_home.di

import android.content.SharedPreferences
import com.singhtwenty2.konix.feature_home.data.remote.CompanyRemoteService
import com.singhtwenty2.konix.feature_home.data.repository.CompanyRepositoryImpl
import com.singhtwenty2.konix.feature_home.domain.repository.CompanyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun providesCompanyService(): CompanyRemoteService {
        return Retrofit.Builder()
            .baseUrl("http://172.20.10.2:7777/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(CompanyRemoteService::class.java)
    }

    @Provides
    @Singleton
    fun providesHomeRepository(
        companyRemoteService: CompanyRemoteService,
        sharedPreferences: SharedPreferences
    ): CompanyRepository {
        return CompanyRepositoryImpl(companyRemoteService, sharedPreferences)
    }
}