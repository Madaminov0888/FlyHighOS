package org.flyhigh.os.AdminPanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.composables.core.Icon
import org.flyhigh.os.AuthViews.TextFieldBoxes
import org.flyhigh.os.Components.PrimaryButton
@Composable
fun AdminAuthView(vm: AdminAuthViewModel, navController: NavController) {
    val canLogin = vm.canLogin.collectAsState().value

    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            IconButton(
                onClick = { navController.popBackStack() }, // Handle back navigation
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart) // Align the button to the top-left corner
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            // Main content of the view
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp), // Add padding to avoid overlapping with the back button
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(size = 10.dp))
                        .background(Color.LightGray)
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(space = 40.dp),
                ) {
                    Text(text = "Welcome back, Admin", color = Color.Black, fontSize = 25.sp)

                    TextFieldBoxes(text = vm.login, title = "Email", action = { newText -> vm.updateLogin(newText) })

                    TextFieldBoxes(text = vm.loginPassword, title = "Password", action = { newText -> vm.updateLoginPassword(newText) })

                    PrimaryButton(
                        text = "Login",
                        backgroundColor = Color.Black,
                        disabled = !canLogin,
                        textColor = Color.White,
                        onClick = {
                            navController.navigate("adminHome")
                        }
                    )
                }
            }
        }
    }
}