package com.example.weatherapp

import android.media.AsyncPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.WeatherModel

@Composable
fun WeatherPage(viewModel: WeatherViewModel) {
    var search by remember {
        mutableStateOf("")
    }
    val weatherResult = viewModel.wtherrs.observeAsState()
Column (modifier = Modifier
    .safeContentPadding()
    .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
        OutlinedTextField(modifier = Modifier.weight(1f),value = search , onValueChange = {
            search = it},
            label = {
                Text(text = "Any Location?")
            }
        )
        IconButton(onClick = { viewModel.getData(search) }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
        }
    when(val result = weatherResult.value){
        is NetworkResponse.Error -> {
            Text(text = result.message)
        }
        NetworkResponse.Loading -> {
            CircularProgressIndicator()
        }
        is NetworkResponse.Success -> {
            WeatherDetails(data = result.data)
        }
        null -> {}
    }
    }
}

@Composable
fun WeatherDetails(data:WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ){
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null,
                modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.padding(3.dp))
            Text(text = data.location.name, fontSize = 30.sp)
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = data.location.country, fontSize = 20.sp, modifier = Modifier.padding(top = 10.dp))
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Text(text = "${data.current.temp_c} ° C",
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            AsyncImage(modifier = Modifier.size(150.dp),model = "https:${data.current.condition.icon}".replace("64x64", "128x128")
                , contentDescription = null)
            Text(text = data.current.condition.text,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Card {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                        WeatherKeyVal("Humidity", data.current.humidity.toString())
                        WeatherKeyVal("Wind Speed", data.current.wind_kph + " km/h")
                        }
                    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                        WeatherKeyVal("UV", data.current.uv)
                        WeatherKeyVal("Participation", data.current.precip_mm + " mm")
                    }
                    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                        WeatherKeyVal("Local Time", data.location.localtime.split(" ")[1])
                        WeatherKeyVal("Local Date", data.location.localtime.split(" ")[0])
                    }
                    }
                }
            }
        }
    }

@Composable
fun WeatherKeyVal(key:String, value:String) {
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 24.sp, fontWeight =  FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
}
}


