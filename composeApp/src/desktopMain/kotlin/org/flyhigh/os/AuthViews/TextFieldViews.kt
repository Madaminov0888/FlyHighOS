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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import java.util.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextFieldViews(vm: TextFieldViewModel = TextFieldViewModel()) {
    MaterialTheme {
        var registered by remember { mutableStateOf(true) }

        val padding by animateDpAsState(
            targetValue = if (registered) 40.dp else 220.dp,
            animationSpec = tween(500)
        )

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
                    horizontalArrangement = Arrangement.Center) {

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

                    Text("Register",
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = buttonBackgroundColor)
                            .padding(10.dp)
                            .onClick {
                                registered = !registered
                                     }
                        ,
                        color = buttonTextColor,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Text("Login",
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = buttonBackgroundColor2)
                            .padding(10.dp)
                            .onClick {
                                registered = !registered
                            }
                        ,
                        color = buttonTextColor2,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                    )

                }
            }

            item {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    AnimatedVisibility(
                        visible = registered,
                        enter = fadeIn(animationSpec = tween(500)) + slideInHorizontally(),
                        exit = fadeOut(animationSpec = tween(500)) + scaleOut()
                    ) {
                        RegisterView(vm)
                    }

                    AnimatedVisibility(
                        visible = !registered,
                        enter = fadeIn(animationSpec = tween(500)) + slideInHorizontally(),
                        exit = fadeOut(animationSpec = tween(500)) + scaleOut()
                    ) {
                        LoginPageView(vm)
                    }
                }
            }
        }
    }
}




@Composable
fun TextFieldBoxes(text: StateFlow<String>, title: String, action: (txt: String)->Unit, readOnly: Boolean = false) {
    MaterialTheme {
        val textValue by text.collectAsState()

        TextField(value = textValue, onValueChange = {newText -> action(newText)}, label = {
            Text(title)
        }, modifier = Modifier
            .padding()
            .widthIn(400.dp, 450.dp)
            ,
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                backgroundColor = Color.White
            ),
            singleLine = true,
            readOnly = readOnly,
            enabled = !readOnly
        )
    }
}

