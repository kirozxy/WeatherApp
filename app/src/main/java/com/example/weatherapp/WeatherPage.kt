package com.example.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.dp
import com.example.weatherapp.api.NetworkResponse

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
            Text(text = result.data.toString())
        }
        null -> {}
    }
    }
}