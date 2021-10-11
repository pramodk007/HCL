package com.example.heartcarelite.ui.main

import androidx.lifecycle.MutableLiveData

class MainViewModel {

    val countryName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}