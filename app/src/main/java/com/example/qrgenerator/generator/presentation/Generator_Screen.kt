package com.example.qrgenerator.generator.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.qrgenerator.core.presentation.SimpleTextField
import com.example.qrgenerator.R
import com.example.qrgenerator.generator.presentation.viewModel.VMQRGenerator

@Composable
fun Generator_SC(navController: NavHostController, viewModel: VMQRGenerator){
    val bitmap: Bitmap? = viewModel.barcode.value
    var showQRReader: Boolean by remember {
        mutableStateOf(false)
    }
    var qrText: String by remember {
        mutableStateOf("")
    }

    Scaffold {innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)){
            Text(
                text = qrText,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))

            if (bitmap != null)
            {
                ShowBarcode(innerPadding, bitmap)
            }
            else {
                CreateFields(innerPadding, viewModel)
            }
        }
    }
}

@Composable
fun CreateFields(scaffoldPadding: PaddingValues, viewModel: VMQRGenerator) {
    val fullName: String by viewModel.fullName.observeAsState(initial = "")

    Column(modifier = Modifier
//        .fillMaxSize()
        .padding(scaffoldPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleTextField(
            value = fullName,
            title = "Nombre completo",
            enabled = true,
            icon = painterResource(
                    id = R.drawable.person
                ),
            onChange = { viewModel.onNameChange(it) })
        Button(onClick = { viewModel.createQR() }) {
            Text(text = "Generar código")
        }
    }
}

@Composable
fun ShowBarcode(innerPadding: PaddingValues, barcode: Bitmap) {
    Image(
        bitmap = barcode.asImageBitmap(),
        contentDescription = null,
        modifier = Modifier.fillMaxSize())
}