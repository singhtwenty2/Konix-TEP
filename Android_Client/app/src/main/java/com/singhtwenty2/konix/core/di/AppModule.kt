package com.singhtwenty2.konix.core.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.singhtwenty2.konix.feature_auth.data.remote.AuthCookieJar
import com.singhtwenty2.konix.feature_auth.data.remote.AuthRemoteService
import com.singhtwenty2.konix.feature_auth.data.repository.AuthRepositoryImpl
import com.singhtwenty2.konix.feature_auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesAuthService(): AuthRemoteService {
        val cookieJar = AuthCookieJar()
        val okHttpClient = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .build()
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.168:7777/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthRemoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(
        authRemoteService: AuthRemoteService,
        sharedPreferences: SharedPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(authRemoteService, sharedPreferences)
    }
}