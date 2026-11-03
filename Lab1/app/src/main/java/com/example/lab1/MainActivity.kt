package com.example.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab1.ui.theme.Lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1Theme {
                Lab1App()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun Lab1App() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.MAIN) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painterResource(it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.MAIN -> MainScreen(modifier = Modifier.padding(innerPadding))
                AppDestinations.COUNTER -> CounterScreen(modifier = Modifier.padding(innerPadding))
                AppDestinations.STUDENT -> StudentScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    MAIN("Головна", R.drawable.ic_home),
    COUNTER("Лічильник", R.drawable.ic_favorite),
    STUDENT("Студент", R.drawable.ic_account_box),
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var message by remember { mutableStateOf("Привіт! Це головна сторінка") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = { message = "Текст змінено після натискання!" },
            modifier = Modifier
                .width(220.dp)
                .height(50.dp)
        ) {
            Text(text = "Змінити текст", fontSize = 16.sp)
        }
    }
}

@Composable
fun CounterScreen(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableIntStateOf(0) }

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
            onClick = { count++ },
            modifier = Modifier
                .width(160.dp)
                .height(60.dp)
        ) {
            Text(text = "Додати +1", fontSize = 20.sp)
        }
    }
}

@Composable
fun StudentScreen(modifier: Modifier = Modifier) {
    var studentInfo by rememberSaveable { mutableStateOf("Інформація про студента прихована") }

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
            onClick = { studentInfo = "Лабораторну роботу №1 виконано!" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Показати статус", fontSize = 14.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Lab1Theme {
        MainScreen()
    }
}