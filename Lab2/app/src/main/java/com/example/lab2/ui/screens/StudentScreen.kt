package com.example.lab2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun StudentScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
) {
    val studentInfo by appViewModel.studentInfo.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = studentInfo,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Button(
            onClick = {
                appViewModel.updateStudentInfo("Лабораторну роботу виконано!")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Показати статус", fontSize = 14.sp)
        }
    }
}
