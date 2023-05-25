package com.example.qrgenerator.main.domain.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qrgenerator.main.domain.model.QREvent

class MainVM: ViewModel() {
    private val _eventsList = MutableLiveData<QREvent>()
}