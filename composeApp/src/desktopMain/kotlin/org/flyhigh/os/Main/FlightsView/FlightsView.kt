package org.flyhigh.os.Main.FlightsView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.flyhigh.os.Main.HomeViewModel
import org.flyhigh.os.Main.SearchFieldView

@Composable
fun FlightsView(navController: NavController, vm: HomeViewModel) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SearchFieldView(vm, navController)
        }
    }
}