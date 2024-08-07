package com.example.weather.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import  androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import com.example.weather.data.WeatherViewModel

@Composable
fun Settings(weatherViewModel: WeatherViewModel) {


    val isDarkTheme by weatherViewModel.isDarkTheme.observeAsState(false)

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 125.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp),
            verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dark Mode",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 19.sp
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = {
                        weatherViewModel.toggleTheme(it)

                    },
                    thumbContent = if (isDarkTheme) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "OK",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    } else {
                        null
                    }
                )
            }
        }
    }

}





