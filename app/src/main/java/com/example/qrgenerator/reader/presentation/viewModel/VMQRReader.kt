package com.example.qrgenerator.reader.presentation.viewModel

import androidx.compose.animation.core.DecayAnimation
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.qrgenerator.reader.domain.use_case.DecodeQR
import kotlin.math.exp

class VMQRReader: ViewModel() {

    /**Bandera que indica que debe mostrar la información del QR.
     * Si está activa, muestra la información obtenida; si no, muestra el lector de QR*/
    val mostrarInfo: MutableState<Boolean> = mutableStateOf(false)

    /**Mensaje de error*/
    val errorMessage: MutableState<String?> = mutableStateOf(null)

    /**Número de ticket*/
    val ticketNumber: MutableState<Int> = mutableStateOf(0)
    /**Nombre del evento*/
    val eventName: MutableState<String> = mutableStateOf("")
    /**Fecha de expiración*/
    val expirationDate: MutableState<String> = mutableStateOf("")
    /**Observaciones o notas extras*/
    val notes: MutableState<String> = mutableStateOf("")

    fun readQR(qrString: String){
        val data: Array<String> = DecodeQR().invoke(qrString)
        return try {
            if (data.size < 4) throw Exception("Invalid QR code")
            ticketNumber.value = data[0].toIntOrNull() ?: throw Exception("Invalid QR code")
            eventName.value = data[1]
            expirationDate.value = data[2]
            notes.value = data[3]
            mostrarInfo.value = true
        } catch (e: Exception) {
            errorMessage.value = e.message
        }
    }

}