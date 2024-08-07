package com.example.weather

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weather.data.WeatherViewModel
import com.example.weather.data.WeatherViewModelFactory
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.utils.ThemePreference
import dagger.hilt.android.AndroidEntryPoint
import java.util.prefs.Preferences
import javax.inject.Inject

//http://api.weatherapi.com/v1/current.json?key=3c9b3c48b69a40f1bce123153243007&q=Bukkapatnam&aqi=no

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var weatherViewModelFactory: WeatherViewModelFactory
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        init()
        setContent {

            val isDarkTheme by weatherViewModel.isDarkTheme.observeAsState(false)
            var route by remember {
                mutableStateOf("")
            }
            val navController : NavHostController = rememberNavController()
            WeatherTheme (darkTheme = isDarkTheme){
                Scaffold(
                    topBar = {
                        when(route){
                            "home" -> HomeNavigation(navHostController = navController)
                            else -> SettingsNavigation(navHostController = navController)
                        }
                    }
                ) { innerPadding ->
                   Navigation(
                       navController = navController,
                       weatherViewModel = weatherViewModel,

                       modifier = Modifier
                           .padding
                               (innerPadding)
                   ){
                       route = it
                   }
                }
            }

    }
    }
    private fun init(){
        weatherViewModel = ViewModelProvider(this,weatherViewModelFactory)[WeatherViewModel::class.java]
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavigation(navHostController: NavHostController){
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = {
            Text(
                "Weather",
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        actions = { IconButton(onClick = { navHostController.navigate("settings") }){
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Shopping " +
                        "cart",
                modifier = Modifier.size(35.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsNavigation(navHostController: NavHostController){
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        navigationIcon = {
            IconButton(onClick = {navHostController.navigate("home")}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Your back icon resource
                    contentDescription = "navigation back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        title = {
            Text(
                "Settings",
                color = MaterialTheme.colorScheme.onBackground
            )
        },
    )
}