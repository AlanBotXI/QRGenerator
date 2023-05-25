package com.example.qrgenerator.generator.domain.use_case

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

class GenerateQR {

    operator fun invoke(fullName: String): Bitmap{
        val ticketNumber: Int = getTicketID()
        val expirationDate: String = getExpirationDate()
        return getQrCodeBitmap(ticketNumber = ticketNumber, name = fullName, expirationDate = expirationDate)
    }

    private fun getExpirationDate(): String {
        return "2023-01-01"
    }

    private fun getTicketID(): Int {
        return 1
    }

    private fun getQrCodeBitmap(ticketNumber: Int, name: String, expirationDate: String): Bitmap {
        val size = 512 //pixels
        val qrCodeContent = "Ticket number:$ticketNumber;Name:$name;ExpirationDate:$expirationDate;"
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}