package com.singhtwenty2.konix.feature_home.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
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
            .baseUrl("http://192.168.0.168:7777/")
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