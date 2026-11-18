package com.example.lab2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.lab2.AppViewModel

sealed interface MainSubRoute {
    data object SubScreen1 : MainSubRoute
    data object SubScreen2 : MainSubRoute
    data object SubScreen3 : MainSubRoute
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
) {
    val backStack = remember { mutableStateListOf<MainSubRoute>(MainSubRoute.SubScreen1) }

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = entryProvider {
            entry<MainSubRoute.SubScreen1> {
                MainSubScreen1(
                    appViewModel = appViewModel,
                    onNavigateToSecond = { backStack.add(MainSubRoute.SubScreen2) }
                )
            }

            entry<MainSubRoute.SubScreen2> {
                MainSubScreen2(
                    onNavigateToThird = { backStack.add(MainSubRoute.SubScreen3) },
                    onBack = { backStack.removeLastOrNull() }
                )
            }

            entry<MainSubRoute.SubScreen3> {
                MainSubScreen3(
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}

@Composable
fun MainSubScreen1(
    appViewModel: AppViewModel,
    onNavigateToSecond: () -> Unit
) {
    val message by appViewModel.message.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
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
            onClick = {
                appViewModel.updateMessage("Текст змінено через ViewModel!")
            },
            modifier = Modifier
                .width(240.dp)
                .height(50.dp)
        ) {
            Text(text = "Змінити текст", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onNavigateToSecond,
            modifier = Modifier
                .width(240.dp)
                .height(50.dp)
        ) {
            Text(text = "На другий підекран ->", fontSize = 16.sp)
        }
    }
}

@Composable
fun MainSubScreen2(
    onNavigateToThird: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Другий підекран",
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = "Це довільна розмітка другого підекрана (Jetpack Navigation 3).",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(
            onClick = onNavigateToThird,
            modifier = Modifier
                .width(240.dp)
                .height(50.dp)
        ) {
            Text(text = "На третій підекран ->", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .width(240.dp)
                .height(50.dp)
        ) {
            Text(text = "<- Назад", fontSize = 16.sp)
        }
    }
}

@Composable
fun MainSubScreen3(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Третій підекран (Деталі)",
            fontSize = 26.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = "Ви успішно здійснили два переходи за допомогою Navigation 3! Натисніть кнопку нижче або системний жест «Назад».",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(
            onClick = onBack,
            modifier = Modifier
                .width(240.dp)
                .height(50.dp)
        ) {
            Text(text = "<- Повернутися назад", fontSize = 16.sp)
        }
    }
}
