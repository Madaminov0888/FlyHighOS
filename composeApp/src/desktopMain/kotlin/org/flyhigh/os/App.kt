package org.flyhigh.os

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import flyhigh.composeapp.generated.resources.Res
import flyhigh.composeapp.generated.resources.compose_multiplatform
import org.example.project.AuthViews.TextFieldViewModel
import org.example.project.AuthViews.TextFieldViews
import org.flyhigh.os.Main.HomeView
import org.flyhigh.os.Main.HomeViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        var authVM = TextFieldViewModel()
        var homeVM = HomeViewModel()
        val navController = rememberNavController()

        NavHost(navController, startDestination = "auth") {
            composable(route = "auth") {
                TextFieldViews(authVM, navController)
            }

            composable(route = "home") {
                HomeView(homeVM, navController)
            }
        }
    }
}