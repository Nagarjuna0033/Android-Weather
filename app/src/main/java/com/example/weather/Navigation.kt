package com.example.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.weather.data.WeatherViewModel
import com.example.weather.presentation.screen.Home
import com.example.weather.presentation.screen.Settings
import com.example.weather.utils.ThemePreference

@Composable
fun Navigation(navController: NavHostController,weatherViewModel: WeatherViewModel,modifier:
Modifier,onClick:(route : String)->Unit){
   NavHost(navController=navController,startDestination="home"){

      composable("home"){
         Home(weatherViewModel=weatherViewModel,modifier = modifier)
         onClick("home")
      }
      composable("settings"){
         Settings(weatherViewModel = weatherViewModel)
         onClick("settings")
      }

   }
}