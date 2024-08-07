package com.example.weather.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.model.WeatherDataModel
import com.example.weather.domain.repository.Repo
import com.example.weather.utils.ThemePreference
import com.example.weather.utils.ThemePreferenceClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo : Repo, application: Application) : ViewModel() {
    private val themePreference = ThemePreference(application)

    var weatherLiveData = MutableLiveData<WeatherDataModel?>()
    var isLoading = mutableStateOf(false);
    var location = mutableStateOf("")
    var searchAttempted = mutableStateOf(false)

    val isDarkTheme = MutableLiveData(themePreference.getThemePreference()
        .isDarkTheme)
//    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    fun toggleTheme(isDark: Boolean) {
        isDarkTheme.postValue(isDark)
        themePreference.setThemePreference(ThemePreferenceClass(isDark))
    }

    fun getWeather(){
        isLoading.value = true
        searchAttempted.value = true
        viewModelScope.launch (Dispatchers.IO){
            Log.d("locationWeather",location.toString())
            val response = repo.getWeatherDetails(location.value)
            Log.d("locationWeather",response.toString())
            if(response.isSuccessful){
                weatherLiveData.postValue(response.body())
                Log.d("locationWeather",response.body().toString())
                isLoading.value = false
                location.value = ""
            }else{
                Log.d("weather","not available")
                weatherLiveData.postValue(null)
                isLoading.value = false;
            }
        }
    }
}




