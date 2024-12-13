package org.flyhigh.os.Main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeView(vm: HomeViewModel, navController: NavController) {
    MaterialTheme {
        Text("Home View")
    }
}