package org.flyhigh.os.Main.Payment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.flyhigh.os.Main.FlightsView.SelectedSeats
import org.flyhigh.os.Main.HomeViewModel
import org.flyhigh.os.Models.FlightCombination


@Composable
fun PaymentView(
    homeVM: HomeViewModel,
    paymentVM: PaymentViewModel,
    flightCombination: FlightCombination,
    selectedSeats: List<SelectedSeats>,
    navController: NavController
) {
    val adultNumber by homeVM.adultNumber.collectAsState()
    val childrenNumber by homeVM.childrenNumber.collectAsState()
    val infantsNumber by homeVM.infantsNumber.collectAsState()

    // Calculate total price
    val totalPassengers = adultNumber + childrenNumber + infantsNumber
    val totalPrice = flightCombination.flightsOverallPrice * totalPassengers

    // Mutable states to store user input
    var cardHolderName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Payment",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Payment form
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Card Holder Name:")
                TextField(
                    value = cardHolderName,
                    onValueChange = { cardHolderName = it },
                    placeholder = { Text("Card Holder Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Card Number:")
                TextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    placeholder = { Text("1234 5678 9101 1121") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Expiration Date")
                        TextField(
                            value = expirationDate,
                            onValueChange = { expirationDate = it },
                            placeholder = { Text("MM/YY") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("CVV")
                        TextField(
                            value = cvv,
                            onValueChange = { cvv = it },
                            placeholder = { Text("123") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Total price and action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$${"%.2f".format(totalPrice)} total price",
                    style = MaterialTheme.typography.h6
                )
                Button(
                    onClick = {
                        // Handle payment logic here
                        paymentVM.processPayment(
                            cardHolderName = cardHolderName,
                            cardNumber = cardNumber,
                            expirationDate = expirationDate,
                            cvv = cvv,
                            totalPrice = totalPrice
                        )
                        navController.navigate("home")
                    }
                ) {
                    Text("Pay Now")
                }
            }
        }
    }
}