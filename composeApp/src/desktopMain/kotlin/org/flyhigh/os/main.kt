package org.flyhigh.os

import androidx.compose.material.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.flow.MutableStateFlow

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "FlyHigh",
        state = WindowState(width = 1500.dp, height = 900.dp),
    ) {
        App()
    }
}