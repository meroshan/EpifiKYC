package com.myapp.epifikyc

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {
    //private var _isPanNoValid = MutableStateFlow<Boolean>(false)
    //val isPanNoValid: StateFlow<Boolean> = _isPanNoValid

    private val panNumber = MutableStateFlow("")
    private val birthDay = MutableStateFlow("")
    private val birthMonth = MutableStateFlow("0")
    private val birthYear = MutableStateFlow("0")

    fun setPanNumber(value: String) {
        panNumber.value = value
    }

    fun setBirthDay(value: String) {
        birthDay.value = value
    }

    fun setBirthMonth(value: String) {
        birthMonth.value = value
    }

    fun setBirthYear(value: String) {
        birthYear.value = value
    }

    fun validatePanNumber(panNo: String): Boolean {
        val pattern = Regex("[A-Z]{5}\\d{4}[A-Z]")
        return panNo.matches(pattern)
    }

    private fun validateDate(date: String): Boolean {
        if (date.length < 8)
            return false
        val formatter = SimpleDateFormat("ddMMyyyy", Locale.ENGLISH)
        formatter.isLenient = false
        return try {
            formatter.parse(date)
            true
        } catch (e: ParseException) {
            false
        }
    }

    val isButtonEnabled =
        combine(panNumber, birthDay, birthMonth, birthYear) { pan, day, month, year ->
            val isPanNumberValid = validatePanNumber(pan)
            val date = day + month + year
            val isDateValid = validateDate(date)
            return@combine isPanNumberValid && isDateValid
        }
}


















