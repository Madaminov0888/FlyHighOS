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
import androidx.compose.material.Button
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
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.example.project.AuthViews.UserLogin
import org.flyhigh.os.Components.BackButton
import org.flyhigh.os.Components.PrimaryButton
import org.flyhigh.os.Managers.NetworkManager
import org.flyhigh.os.Models.LoginResponseModel
import org.flyhigh.os.Models.RepresentativeDetails
import org.flyhigh.os.Models.RequestModel
import org.flyhigh.os.Models.ResponseModel
import org.flyhigh.os.TOKEN


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepresentativeAuthView(navController: NavController, networkManager: NetworkManager) {
    val vm = RepresentativeAuthViewModel()

    MaterialTheme {
        var registered by remember { mutableStateOf(true) }
        var popUp by remember { mutableStateOf(false) }
        val canRegister = vm.canRegister.collectAsState().value
        val canLogin = vm.canLogin.collectAsState().value
        val scope = CoroutineScope(Dispatchers.Default + Job())

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

            if (popUp) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Your request is processing",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )

                        Button(onClick = { popUp = false }) {
                            Text("Ok")
                        }
                    }
                }
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
                                scope.launch {
                                    try {
                                        val registerData = RepresentativeDetails(
                                            email = vm.email.value,
                                            password = vm.password.value,
                                            state = "waiting",
                                            name = vm.firstName.value,
                                            logo = null,
                                            contactNumber = vm.phoneNumber.value,
                                            contactEmail = vm.email.value,
                                            address = vm.companyAddress.value,
                                            tin = vm.tin.value,
                                            accountHolderName = vm.holderName.value,
                                            bankName = vm.bankName.value,
                                            bankSwiftCode = vm.SWIFTcode.value,
                                            bankAccountNumber = vm.bankAccountNumber.value,
                                            airlineDescription = ""
                                        )
                                        val requestModel = RequestModel<RepresentativeDetails>(
                                            request = "register-representative",
                                            authorization = null,
                                            details = registerData
                                        )
                                        val loginJson = Json.encodeToJsonElement(requestModel)

                                        networkManager.sendMessage(loginJson)
                                        var response: String? = null
                                        var retryCount = 0

                                        while (response == null && retryCount < 3) {
                                            response = networkManager.readMessage()
                                            if (response == null) {
                                                networkManager.close()
                                                networkManager.connect()
                                                networkManager.sendMessage(loginJson) // Resend login data
                                                retryCount++
                                                delay(1000)
                                            }
                                        }

                                        if (response?.contains("OK") == true) {
                                            val cleanedResponse = response.trimStart { it != '{' && it != '[' }
                                            val responseData =
                                                Json.decodeFromString<ResponseModel<LoginResponseModel>>(cleanedResponse)
                                            withContext(Dispatchers.IO) {
                                                TOKEN.userToken = responseData.result.token
                                                registered = !registered
                                            }
                                        } else if (response?.contains("WAIT") == true) {
                                            popUp = true
                                            registered = !registered
                                        } else {
                                            println("Login failed: $response")
                                        }
                                    } catch (e: Exception) {
                                        println("Error during login: ${e.message}")
                                    }
                                }
                            })
                    } else {
                        PrimaryButton(
                            text = "Login",
                            backgroundColor = Color.Black,
                            textColor = Color.White,
                            disabled = !canLogin,
                            onClick = {
                                scope.launch {
                                    try {
                                        val loginData = UserLogin(email = vm.loginEmail.value, password = vm.loginPassword.value)
                                        val requestModel = RequestModel<UserLogin>(
                                            request = "login-representative",
                                            authorization = null,
                                            details = loginData
                                        )
                                        val loginJson = Json.encodeToJsonElement(requestModel)

                                        networkManager.sendMessage(loginJson)
                                        var response: String? = null
                                        var retryCount = 0

                                        while (response == null && retryCount < 3) {
                                            response = networkManager.readMessage()
                                            if (response == null) {
                                                networkManager.close()
                                                networkManager.connect()
                                                networkManager.sendMessage(loginJson) // Resend login data
                                                retryCount++
                                                delay(1000)
                                            }
                                        }

                                        if (response?.contains("OK") == true) {
                                            val cleanedResponse = response!!.trimStart { it != '{' && it != '[' }
                                            val responseData = Json.decodeFromString<ResponseModel<LoginResponseModel>>(cleanedResponse)
                                            TOKEN.userToken = responseData.result.token
                                            withContext(Dispatchers.IO) {
                                                navController.navigate("representativeHome")
                                            }
                                        } else {
                                            println("Login failed: $response")
                                        }
                                    } catch (e: Exception) {
                                        println("Error during login: ${e.message}")
                                    }
                                }
//                                navController.navigate(route = "representativeHome")
                            })
                    }
                }
            }
        }
    }
}