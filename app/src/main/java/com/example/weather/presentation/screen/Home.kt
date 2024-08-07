package com.example.weather.presentation.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.weather.data.WeatherViewModel
import com.example.weather.domain.model.WeatherDataModel
import com.example.weather.utils.FieldValidator

//https://cdn.weatherapi.com/weather/128x128/day/116.png
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home (weatherViewModel: WeatherViewModel,modifier : Modifier){
    val weather by weatherViewModel.weatherLiveData.observeAsState()
    val searchAttempted by weatherViewModel.searchAttempted
    val focusManager = LocalFocusManager.current
    val focusRequest = remember {
        FocusRequester()
    }
    Box(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme
            .primaryContainer).pointerInput(Unit) {
            detectTapGestures(onTap = { focusManager.clearFocus() })
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth().padding(top = 15.dp)

        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    maxLines = 1,
                    label = {
                        Text(
                            text = "Enter Location",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 17.sp
                        )
                    },
                    value = weatherViewModel
                        .location.value,
                    onValueChange = {
                        weatherViewModel
                            .location.value = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedBorderColor = MaterialTheme.colorScheme
                            .secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme
                            .secondary,
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    modifier = Modifier.focusRequester(focusRequest).width(310.dp)

                )
                Button(
                    onClick = { focusManager.clearFocus(); if(FieldValidator.isValid(weatherViewModel.location.value))
                        weatherViewModel
                        .getWeather() },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults
                        .buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.height(45.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search",
                        modifier = Modifier.size(25.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }

    if(weatherViewModel.isLoading.value){
        Loader()
    }else{
        if(searchAttempted && weather == null){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(
                    text = "Location not Found",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }else{
            weather?.let { WeatherDetails(weatherDataModel = it) }
        }
    }
}



@Composable
fun WeatherDetails(weatherDataModel : WeatherDataModel){
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .crossfade(true)
        .build()

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https:"+weatherDataModel.current.condition.icon)
            .build(),
        imageLoader = imageLoader
    )
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment
                    .CenterHorizontally
            ) {
                Text(
                    text = weatherDataModel.location.name, fontSize = 19.sp, fontWeight = FontWeight
                        .Bold
                )
                Text(
                    text = weatherDataModel.location.region,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 9.dp)
                )
                Text(
                    text = weatherDataModel.location.country,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }

            Image(
                painter = painter, contentDescription =
                "weather icon", modifier = Modifier
                    .width(105.dp)
                    .height(105.dp)
            )
            Text(
                text = weatherDataModel.current.condition.text, fontSize = 19.sp, fontWeight =
                FontWeight
                    .Bold
            )
            Text(
                text = weatherDataModel.current.temp_c.toString()+"°C",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Loader(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        CircularProgressIndicator(
            modifier = Modifier
                .width(50.dp),
            color = MaterialTheme.colorScheme.onBackground,
            strokeWidth = 5.dp
        )
    }

}

//"condition": {
//    "text": "Partly cloudy",
//    "icon": "//cdn.weatherapi.com/weather/64x64/day/116.png",
//    "code": 1003
//},


//"location": {
//    "name": "London",
//    "region": "City of London, Greater London",
//    "country": "United Kingdom",
//    "lat": 51.52,
//    "lon": -0.11,
//    "tz_id": "Europe/London",
//    "localtime_epoch": 1613896955,
//    "localtime": "2021-02-21 8:42"
//},