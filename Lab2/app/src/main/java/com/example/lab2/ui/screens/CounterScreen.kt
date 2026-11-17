package com.example.lab2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab2.AppViewModel

@Composable
fun CounterScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
) {
    val count by appViewModel.count.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Кількість натискань: $count",
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(
            onClick = {
                appViewModel.incrementCount()
            },
            modifier = Modifier
                .width(160.dp)
                .height(60.dp)
        ) {
            Text(text = "Додати +1", fontSize = 20.sp)
        }
    }
}
