package com.example.weather.domain.repository

import com.example.weather.core.network.ApiServices
import javax.inject.Inject

class Repo @Inject constructor(private val apiServices: ApiServices){
    suspend fun getWeatherDetails(city : String) = apiServices.getWeather(city)


}