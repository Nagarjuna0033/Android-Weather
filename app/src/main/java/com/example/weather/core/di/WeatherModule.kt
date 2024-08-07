package com.example.weather.core.di

import android.content.Context
import com.example.weather.core.network.ApiServices
import com.example.weather.domain.repository.Repo
import com.example.weather.utils.ThemePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class WeatherModule {
    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit):ApiServices{
        return retrofit.create(ApiServices::class.java)
    }

    @Singleton
    @Provides
    fun provideRepo(apiServices: ApiServices): Repo {
        return Repo(apiServices)
    }

}