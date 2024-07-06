package com.example.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeatherPage(viewModel: WeatherViewModel) {
    var search by remember {
        mutableStateOf("")
    }
Column (modifier = Modifier.safeContentPadding().fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally){
    Row (modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
        OutlinedTextField(modifier = Modifier.weight(8f),value = search , onValueChange = {
            search = it},
            label = {
                Text(text = "Any Location?")
            }
        )
        IconButton(onClick = { viewModel.getData(search) }, modifier = Modifier.weight(2f)) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    }
}
}