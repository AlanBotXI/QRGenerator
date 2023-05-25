package com.example.qrgenerator.reader.domain.use_case

import android.content.res.TypedArray

class DecodeQR {
    operator fun invoke(qrString: String): Array<String> {
        return qrString.split("&").toTypedArray()
    }
}