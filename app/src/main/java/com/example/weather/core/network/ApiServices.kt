package com.example.weather.core.network

import com.example.weather.domain.model.WeatherDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("/v1/current.json?key=3c9b3c48b69a40f1bce123153243007")
    suspend fun getWeather(@Query("q") city: String) : Response<WeatherDataModel>

}