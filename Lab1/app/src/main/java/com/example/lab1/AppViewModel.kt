package com.example.lab1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {

    // Класичний патерн інкапсуляції StateFlow (як у Demo2026 для myFavoritesText)
    private val _message = MutableStateFlow("Привіт! Це головна сторінка")
    val message: StateFlow<String> = _message.asStateFlow()

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    private val _studentInfo = MutableStateFlow("Інформація про студента прихована")
    val studentInfo: StateFlow<String> = _studentInfo.asStateFlow()

    // Методи оновлення стану
    fun updateMessage(newMessage: String) {
        _message.value = newMessage
    }

    fun incrementCount() {
        _count.value++
    }

    fun updateStudentInfo(newInfo: String) {
        _studentInfo.value = newInfo
    }
}

