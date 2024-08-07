package com.example.weather.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.domain.repository.Repo
import javax.inject.Inject

class WeatherViewModelFactory @Inject constructor(
    private val repo : Repo,
    private val application: Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(repo, application) as T
    }
}