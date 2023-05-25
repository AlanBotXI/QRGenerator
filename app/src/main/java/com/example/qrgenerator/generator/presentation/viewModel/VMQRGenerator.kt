package com.example.qrgenerator.generator.presentation.viewModel

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qrgenerator.generator.domain.use_case.GenerateQR

class VMQRGenerator: ViewModel() {

    val errorMessage: MutableState<String?> = mutableStateOf(value = null)

    private val _fullName = MutableLiveData<String>()
    val fullName: LiveData<String> = _fullName

    fun onNameChange(value: String): Boolean {
        _fullName.value = value
        return true
    }

    val barcode: MutableState<Bitmap?> = mutableStateOf(value = null)

    fun createQR(){
        try {
            barcode.value = GenerateQR().invoke(
                fullName = _fullName.value
                    ?: throw Exception("debe indicar su nombre completo"))

        } catch (e: Exception) {
            errorMessage.value = e.message
        }
    }
}