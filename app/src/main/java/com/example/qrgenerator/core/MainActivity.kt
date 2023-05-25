package com.example.qrgenerator.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.qrgenerator.core.global.ViewModels
import com.example.qrgenerator.core.navigation.AppNavigation
import com.example.qrgenerator.core.presentation.ui.theme.QRGeneratorTheme
import com.example.qrgenerator.generator.presentation.Generator_SC
import com.example.qrgenerator.generator.presentation.viewModel.VMQRGenerator
import com.example.qrgenerator.reader.presentation.screen.Reader_SC
import com.example.qrgenerator.reader.presentation.viewModel.VMQRReader
import java.util.concurrent.ExecutorService

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ViewModel de generador de QR
        val vmGenerator: VMQRGenerator = ViewModelProvider(this)[VMQRGenerator::class.java]
        ViewModels.vmGenerator = vmGenerator
        //ViewModel de reader de QR
        val vmReader: VMQRReader = ViewModelProvider(this)[VMQRReader::class.java]
        ViewModels.vmReader = vmReader

        setContent {
            QRGeneratorTheme {
                Reader_SC(/*navController = null,*/ viewModel = vmReader)
//                AppNavigation()
            }
        }
    }

}