package org.flyhigh.os

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.json.Json
import org.example.project.AuthViews.TextFieldViewModel
import org.example.project.AuthViews.TextFieldViews
import org.flyhigh.os.AdminPanel.AdminAuthView
import org.flyhigh.os.AdminPanel.AdminAuthViewModel
import org.flyhigh.os.AdminPanel.AdminHomeView
import org.flyhigh.os.AdminPanel.AdminHomeViewModel
import org.flyhigh.os.Main.FlightsView.*
import org.flyhigh.os.Main.HomeView
import org.flyhigh.os.Main.HomeViewModel
import org.flyhigh.os.Main.Payment.PaymentView
import org.flyhigh.os.Main.Payment.PaymentViewModel
import org.flyhigh.os.Main.Payment.TicketFillData
import org.flyhigh.os.Main.ProfilePage.ProfilePageView
import org.flyhigh.os.Main.ProfilePage.ProfileViewModel
import org.flyhigh.os.Managers.NetworkManager
import org.flyhigh.os.Models.FlightCombination
import org.flyhigh.os.RepresentativePanel.Auth.RepresentativeAuthView
import org.flyhigh.os.RepresentativePanel.Main.AddFlightsView
import org.flyhigh.os.RepresentativePanel.Main.CompanyView
import org.flyhigh.os.RepresentativePanel.Main.RepresentativeHomeView
import org.flyhigh.os.RepresentativePanel.Main.RepresentativeHomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val authVM = TextFieldViewModel()
        val homeVM = HomeViewModel()
        val adminAuthVM = AdminAuthViewModel()
        val adminHomeVM = AdminHomeViewModel()
        val profileVM = ProfileViewModel()
        val detailsVM = DetailsViewModel()
        val seatVM = SeatPlacesViewModel()
        val paymentVM = PaymentViewModel()
        val representativeHomeVM = RepresentativeHomeViewModel()

        val networkManager = NetworkManager.getInstance()

        val navController = rememberNavController()

        LaunchedEffect(Unit) {
            networkManager.connect()
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
                AdminHomeView(adminHomeVM, navController)
            }

            composable(route = "representativeAuth") {
                RepresentativeAuthView(navController, networkManager)
            }


            composable(route = "getFlights") {
                FlightsView(navController, homeVM)
            }

            composable(route = "profilePage") {
                ProfilePageView(profileVM, navController)
            }


            composable(route = "detailsView/{obj}") { backStackEntry ->
                val flightCombination = backStackEntry.arguments?.getString("obj")?.let { Json.decodeFromString<FlightCombination>(it) }
                if (flightCombination != null) {
                    DetailsView(flightCombination, navController, detailsVM)
                }
            }

            composable(route = "seatPlaces/{obj}") { backStackEntry ->
                val flightCombination = backStackEntry.arguments?.getString("obj")?.let { Json.decodeFromString<FlightCombination>(it) }
                if (flightCombination != null) {
                    SeatPlacesView(seatsVM = seatVM, homeVM, navController, flightCombination)
                }
            }

            composable(route = "ticketFillData/{obj}/{sbj}") { backStackEntry ->
                val flightCombination = backStackEntry.arguments?.getString("obj")?.let { Json.decodeFromString<FlightCombination>(it) }
                val sbjString = backStackEntry.arguments?.getString("sbj")?.let { Json.decodeFromString<List<SelectedSeats>>(it) }
                if (flightCombination != null && sbjString != null) {
                    TicketFillData(flightCombination, homeVM, sbjString, navController)
                }
            }

            composable(route = "payment/{obj}/{sbj}") { backStackEntry ->
                val flightCombination = backStackEntry.arguments?.getString("obj")?.let { Json.decodeFromString<FlightCombination>(it) }
                val sbjString = backStackEntry.arguments?.getString("sbj")?.let { Json.decodeFromString<List<SelectedSeats>>(it) }
                if (flightCombination != null && sbjString != null) {
                    PaymentView(homeVM, paymentVM, flightCombination, selectedSeats = sbjString, navController)
                }
            }


            //Representative Views
            composable(route = "representativeHome") {
                RepresentativeHomeView(representativeHomeVM,navController)
            }

            composable(route = "company") {
                CompanyView(representativeHomeVM, navController)
            }

            composable(route = "addFlight") {
                AddFlightsView(vm = representativeHomeVM, navController)
            }
        }
    }
}