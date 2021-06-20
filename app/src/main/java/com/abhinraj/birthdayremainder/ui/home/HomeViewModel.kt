package com.abhinraj.birthdayremainder.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Upcoming Birthdays"
    }
    val text: LiveData<String> = _text
}