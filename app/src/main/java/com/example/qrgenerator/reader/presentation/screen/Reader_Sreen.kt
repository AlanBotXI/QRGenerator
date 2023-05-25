package com.example.qrgenerator.reader.presentation.screen

import android.content.Context
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
//import androidx.navigation.NavController
import com.example.qrgenerator.core.presentation.SimpleTextField
import com.example.qrgenerator.generator.domain.model.QRCodeAnalyzer
import com.example.qrgenerator.reader.presentation.viewModel.VMQRReader

@Composable
fun Reader_SC(/*navController: NavController,*/ viewModel: VMQRReader){
    val showQRReader: Boolean = viewModel.mostrarInfo.value

    Scaffold {innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)){
            if (showQRReader){
                QRReader(viewModel)
            } else {
                MostrarInfoQR(viewModel)
            }
        }
    }
}

@Composable
fun QRReader(viewModel: VMQRReader) {
    var leerQR: Boolean by remember{
        mutableStateOf(false)
    }

    var code by remember{
        mutableStateOf("")
    }

    val errorMessage = viewModel.errorMessage.value

    val context: Context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {granted ->
            hasCameraPermission = granted
        }
    )
    LaunchedEffect(key1 = true, block = {
        launcher.launch(android.Manifest.permission.CAMERA)
    })

    if(hasCameraPermission){
        AndroidView(factory = {context ->
            val previewView = PreviewView(context)
            val preview = Preview.Builder().build()
            val selector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            preview.setSurfaceProvider(previewView.surfaceProvider)
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(previewView.width, previewView.height))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(context),
                QRCodeAnalyzer {result ->
                    code = result
                    if (code != "")
                        viewModel.readQR(code)
                }
            )
            try {
                cameraProviderFuture.get().bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    imageAnalysis
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            previewView
        })
        if (errorMessage != null){
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
        }
    }
}

@Composable
fun MostrarInfoQR(viewModel: VMQRReader) {
    val ticketNumber: Int = viewModel.ticketNumber.value
    val eventName: String = viewModel.eventName.value
    val expirationDate: String = viewModel.expirationDate.value
    val notes: String = viewModel.notes.value

    Row(modifier = Modifier.padding(16.dp)){
        SimpleTextField(
            value = ticketNumber.toString(),
            title = "Ticket number",
            enabled = false,
            icon = null,
            onChange = {false})
    }
    Row(modifier = Modifier.padding(16.dp)){
        SimpleTextField(
            value = eventName,
            title = "Event name",
            enabled = false,
            icon = null,
            onChange = {false})
    }
    Row(modifier = Modifier.padding(16.dp)){
        SimpleTextField(
            value = expirationDate,
            title = "Expiration date",
            enabled = false,
            icon = null,
            onChange = {false})
    }
    Row(modifier = Modifier.padding(16.dp)){
        SimpleTextField(
            value = notes,
            title = "Notes",
            enabled = false,
            icon = null,
            onChange = {false})
    }
}