package com.singhtwenty2.konix.feature_profile.di

import android.content.SharedPreferences
import com.singhtwenty2.konix.feature_profile.data.remote.ProfileRemoteService
import com.singhtwenty2.konix.feature_profile.data.repository.ProfileRepositoryImpl
import com.singhtwenty2.konix.feature_profile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun providesProfileRemoteService(): ProfileRemoteService {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.168:7777/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ProfileRemoteService::class.java)
    }

    @Provides
    @Singleton
    fun providesProfileRepository(
        profileRemoteService: ProfileRemoteService,
        sharedPreferences: SharedPreferences
    ): ProfileRepository {
        return ProfileRepositoryImpl(profileRemoteService, sharedPreferences)
    }

}