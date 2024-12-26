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
import kotlinx.coroutines.delay
import org.example.project.AuthViews.TextFieldViewModel
import org.example.project.AuthViews.TextFieldViews
import org.flyhigh.os.AdminPanel.AdminAuthView
import org.flyhigh.os.AdminPanel.AdminAuthViewModel
import org.flyhigh.os.AdminPanel.AdminHomeView
import org.flyhigh.os.AdminPanel.AdminHomeViewModel
import org.flyhigh.os.Main.FlightsView.FlightsView
import org.flyhigh.os.Main.HomeView
import org.flyhigh.os.Main.HomeViewModel
import org.flyhigh.os.Main.ProfilePage.ProfilePageView
import org.flyhigh.os.Main.ProfilePage.ProfileViewModel
import org.flyhigh.os.Managers.NetworkManager
import org.flyhigh.os.RepresentativePanel.Auth.RepresentativeAuthView
import org.flyhigh.os.RepresentativePanel.Auth.RepresentativeAuthViewModel
import org.flyhigh.os.RepresentativePanel.Main.RepresentativeHomeView

@Composable
@Preview
fun App() {
    MaterialTheme {
        val authVM = TextFieldViewModel()
        val homeVM = HomeViewModel()
        val adminAuthVM = AdminAuthViewModel()
        val adminHomeVM = AdminHomeViewModel()
        val networkManager = NetworkManager.getInstance()
        val profileVM = ProfileViewModel()

        val navController = rememberNavController()

        LaunchedEffect(Unit) {
            networkManager.connect()
        }

        LaunchedEffect(Unit) {
            println("app started")
        }

        NavHost(navController, startDestination = "auth") {
            composable(route = "auth") {
                TextFieldViews(authVM, navController, networkManager)
            }

            composable(route = "home") {
                HomeView(homeVM, navController)
            }

            composable(route = "adminAuth") {
                AdminAuthView(adminAuthVM, navController)
            }

            composable(route = "adminHome") {
                AdminHomeView(adminHomeVM)
            }

            composable(route = "representativeAuth") {
                RepresentativeAuthView(navController)
            }

            composable(route = "representativeHome") {
                RepresentativeHomeView(navController)
            }

            composable(route = "getFlights") {
                FlightsView(navController, homeVM)
            }

            composable(route = "profilePage") {
                ProfilePageView(profileVM, navController)
            }
        }
    }
}