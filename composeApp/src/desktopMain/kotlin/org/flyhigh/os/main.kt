package org.flyhigh.os

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "FlyHigh",
        state = WindowState(width = 1500.dp, height = 900.dp),
    ) {
        App()
    }
}