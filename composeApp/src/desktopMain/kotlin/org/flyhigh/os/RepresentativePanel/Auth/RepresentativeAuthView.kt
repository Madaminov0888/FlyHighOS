package org.flyhigh.os.RepresentativePanel.Auth

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.flyhigh.os.Components.BackButton
import org.flyhigh.os.Components.PrimaryButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepresentativeAuthView(navController: NavController) {
    val vm = RepresentativeAuthViewModel()

    MaterialTheme {
        var registered by remember { mutableStateOf(true) }
        val canRegister = vm.canRegister.collectAsState().value
        val canLogin = vm.canLogin.collectAsState().value

        val padding by animateDpAsState(
            targetValue = if (registered) 40.dp else 220.dp,
            animationSpec = tween(500)
        )

        val buttonBackgroundColor by animateColorAsState(
            targetValue = if (registered) Color.Black else Color.Transparent, animationSpec = tween(delayMillis = 0)
        )

        val buttonTextColor by animateColorAsState(
            targetValue = if (registered) Color.White else Color.Black, animationSpec = tween(delayMillis = 0)
        )

        val buttonBackgroundColor2 by animateColorAsState(
            targetValue = if (!registered) Color.Black else Color.Transparent, animationSpec = tween(delayMillis = 0)
        )

        val buttonTextColor2 by animateColorAsState(
            targetValue = if (!registered) Color.White else Color.Black, animationSpec = tween(delayMillis = 0)
        )

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {

            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                BackButton(navController)

                Text("Log in as Admin",  fontWeight = FontWeight.SemiBold, fontSize = 18.sp,modifier = Modifier
                    .onClick {
                        navController.navigate(route = "adminAuth")
                    })
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .clip(RoundedCornerShape(size = 10.dp))
                    .background(Color.LightGray)
                    .animateContentSize(animationSpec = tween(500)) // Ensure this is set
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {


                item {
                    Row(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        PrimaryButton(text = "Register", backgroundColor = buttonBackgroundColor, textColor = buttonTextColor,onClick = {
                            registered = !registered
                        })

                        PrimaryButton(text = "Login", backgroundColor = buttonBackgroundColor2, textColor = buttonTextColor2, onClick = {
                            registered = !registered
                        })
                    }
                }

                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        this@Column.AnimatedVisibility(
                            visible = registered,
                            enter = fadeIn(animationSpec = tween(500)),
                            exit = fadeOut(animationSpec = tween(500)) + scaleOut()
                        ) {
                            RepresentativeRegisterPage(vm)
                        }

                        this@Column.AnimatedVisibility(
                            visible = !registered,
                            enter = fadeIn(animationSpec = tween(500)),
                            exit = fadeOut(animationSpec = tween(500)) + scaleOut()
                        ) {
                            RepresentativeLoginPage(vm)
                        }
                    }
                }

                item {
                    if (registered) {
                        PrimaryButton(
                            text = "Register",
                            backgroundColor = Color.Black,
                            textColor = Color.White,
                            disabled = !canRegister,
                            onClick = {
                                registered = !registered
                            })
                    } else {
                        PrimaryButton(
                            text = "Login",
                            backgroundColor = Color.Black,
                            textColor = Color.White,
                            disabled = !canLogin,
                            onClick = {
                                navController.navigate(route = "representativeHome")
                            })
                    }
                }
            }
        }
    }
}