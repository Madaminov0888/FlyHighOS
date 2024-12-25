package org.flyhigh.os.Main.ProfilePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.composables.core.Icon
import org.flyhigh.os.AuthViews.DefaultRowView

@Composable
fun ProfilePageView(vm: ProfileViewModel, navController: NavController) {
    MaterialTheme {
        LaunchedEffect(Unit) {
            vm.checkUser {
                navController.popBackStack()
            }
        }

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
                Column(modifier = Modifier.fillMaxSize().padding(10.dp).padding(top = 40.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                    PersonaView(vm)

                    UserInfos(vm)
                }
        }
    }
}


@Composable
fun PersonaView(vm: ProfileViewModel) {
    Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier
                .fillMaxSize(0.1f)
                .padding(horizontal = 20.dp)
            ,
            painter = painterResource(resourcePath = "persona.jpg"),
            contentDescription = "persona",
            contentScale = ContentScale.FillHeight,
        )

        Column {
            Text(vm.mainUser?.firstName + vm.mainUser?.lastName, fontSize = 20.sp, fontWeight = FontWeight.Medium)
            vm.mainUser?.email?.let {
                Text(it, fontSize = 15.sp)
            }
        }
    }
}



@Composable
fun UserInfos(vm: ProfileViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Text("Infos", fontSize = 25.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(20.dp))

        InfoBoxes(label = "First Name", data = vm.mainUser?.firstName)

        InfoBoxes(label = "Last Name", data = vm.mainUser?.lastName)
        InfoBoxes(label = "Email", data = vm.mainUser?.email)
        InfoBoxes(label = "Phone number", data = vm.mainUser?.phoneNumber)
        InfoBoxes(label = "Date of birth", data = vm.mainUser?.birthdate)
        InfoBoxes(label = "Country", data = vm.mainUser?.addressCountry)
        InfoBoxes(label = "City", data = vm.mainUser?.addressCity)
        InfoBoxes(label = "Citizenship", data = vm.mainUser?.citizenship)
        InfoBoxes(label = "State", data = vm.mainUser?.addressState)
        InfoBoxes(label = "Address", data = vm.mainUser?.addressLine1)
        InfoBoxes(label = "Address 2(Optional)", data = vm.mainUser?.addressLine2)
        InfoBoxes(label = "ID card", data = vm.mainUser?.passport)
        InfoBoxes(label = "Foreign Passport ID", data = vm.mainUser?.foreignPassport)
    }
}


@Composable
fun InfoBoxes(label: String, data: String?) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, fontSize = 20.sp, fontWeight = FontWeight.Medium)

        if (data != null) {
            Text(data, fontSize = 18.sp, fontWeight = FontWeight.Light)
        }
    }
}