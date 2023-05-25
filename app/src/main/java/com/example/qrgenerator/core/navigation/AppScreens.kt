package com.example.qrgenerator.core.navigation

sealed class AppScreens(val route: String){
    object ReaderScreen: AppScreens(route = "reader")
    object WriterScreen: AppScreens(route = "writer")
}
