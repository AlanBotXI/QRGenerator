package com.example.qrgenerator.main.domain.model

import java.time.Duration
import java.time.LocalDateTime

/**Data model for application main events. This info will determine the QR code content*/
data class QREvent(
    val eventName: String,
    val creationDate: LocalDateTime,
    val expirationDate: LocalDateTime,
    val tolerance: Duration?
){

}