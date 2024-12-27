package org.example.project.AuthViews

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
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.flyhigh.os.Components.PrimaryButton
import org.flyhigh.os.Managers.NetworkManager
import org.flyhigh.os.Models.LoginResponseModel
import org.flyhigh.os.Models.RegisterUserDetails
import org.flyhigh.os.Models.RequestModel
import org.flyhigh.os.Models.ResponseModel
import org.flyhigh.os.TOKEN


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextFieldViews(vm: TextFieldViewModel = TextFieldViewModel(), navController: NavController, networkManager: NetworkManager) {
    MaterialTheme {
        var registered by remember { mutableStateOf(false) }
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
                Text("Log in as Admin", fontWeight = FontWeight.SemiBold, fontSize = 18.sp,
                    modifier = Modifier
                        .onClick {
                            navController.navigate(route = "adminAuth")
                        }
                )

                Text("Log in as Representative",  fontWeight = FontWeight.SemiBold, fontSize = 18.sp,modifier = Modifier
                    .onClick {
                        navController.navigate(route = "representativeAuth")
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
                            RegisterView(vm)
                        }

                        this@Column.AnimatedVisibility(
                            visible = !registered,
                            enter = fadeIn(animationSpec = tween(500)),
                            exit = fadeOut(animationSpec = tween(500)) + scaleOut()
                        ) {
                            LoginPageView(vm)
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
//                                navController.navigate(route = "home")
                                scope.launch {
                                    try {
                                        val registerData = RegisterUserDetails(
                                            email = vm.email.value,
                                            password = vm.password.value,
                                            firstName = vm.firstName.value,
                                            lastName = vm.lastName.value,
                                            phoneNumber = vm.phoneNumber.value,
                                            birthdate = vm.dateOfBirth.value,
                                            passport = vm.idCard.value,
                                            foreignPassport = vm.foreignPassId.value,
                                            citizenship = vm.citizenship.value,
                                            addressCountry = vm.country.value,
                                            addressCity = vm.city.value,
                                            addressState = vm.state.value,
                                            addressLine1 = vm.address.value,
                                            addressLine2 = vm.address2.value
                                        )
                                        val requestModel = RequestModel<RegisterUserDetails>(
                                            request = "register-user",
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
                                            val responseData = Json.decodeFromString<ResponseModel<LoginResponseModel>>(cleanedResponse)
                                            withContext(Dispatchers.IO) {
                                                TOKEN.userToken = responseData.result.token
                                                navController.navigate("home")
                                            }
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
                                            request = "login-user",
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
                                                navController.navigate("home")
                                            }
                                        } else {
                                            println("Login failed: $response")
                                        }
                                    } catch (e: Exception) {
                                        println("Error during login: ${e.message}")
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
//
//
//import kotlinx.coroutines.*
//import kotlinx.io.core.*
//import kotlinx.io.errors.IOException
//import io.ktor.network.sockets.*
//import io.ktor.network.selector.*
//import io.ktor.utils.io.*
//import io.ktor.utils.io.core.*
//import java.net.Socket
//
//class NetworkManager {
//
//    private val port = 8101
//    private val serverAddress = "192.168.17.200"
//    private var socket: Socket? = null
//    private var readChannel: ByteReadChannel? = null
//    private var writeChannel: ByteWriteChannel? = null
//
//    suspend fun connect() {
//        val selectorManager = SelectorManager(Dispatchers.IO)
//        try {
//            println("CREATING CLIENT SOCKET .....")
//            socket = aSocket(selectorManager).tcp().connect(serverAddress, port)
//            println("CONNECTED TO SERVER AT $serverAddress:$port")
//
//            socket?.let {
//                readChannel = it.openReadChannel()
//                writeChannel = it.openWriteChannel(autoFlush = true)
//
//                println("Hello message sent")
//                sendMessage("Hello from client")
//                println("Ready for Chat....")
//                startChat()
//            }
//        } catch (e: IOException) {
//            println("Connection failed: ${e.localizedMessage}")
//        }
//    }
//
//    private suspend fun startChat() = coroutineScope {
//        val inputJob = launch {
//            while (isActive) {
//                print("CLIENT: ")
//                val message = readLine() ?: continue
//                sendMessage(message)
//                if (message.trim() == "bye") {
//                    println("Closing connection.")
//                    this@coroutineScope.cancel()
//                }
//            }
//        }
//
//        val outputJob = launch {
//            try {
//                while (isActive) {
//                    val serverResponse = readChannel?.readUTF8Line() ?: break
//                    println("SERVER: $serverResponse")
//                }
//            } catch (e: IOException) {
//                println("Error reading server response: ${e.localizedMessage}")
//            }
//        }
//
//        joinAll(inputJob, outputJob)
//    }
//
//    private suspend fun sendMessage(message: String) {
//        try {
//            writeChannel?.writeFully(message.toByteArray(Charsets.UTF_8))
//            writeChannel?.flush()
//        } catch (e: IOException) {
//            println("Error sending message: ${e.localizedMessage}")
//        }
//    }
//
//    suspend fun disconnect() {
//        socket?.close()
//        println("Disconnected from server.")
//    }
//}




