package org.flyhigh.os.Main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.flyhigh.os.AuthViews.DefaultRowView
import org.flyhigh.os.AuthViews.TextFieldBoxes
import org.flyhigh.os.AuthViews.WheelDatePickerBottomSheet
import org.flyhigh.os.Components.CSColors
import org.flyhigh.os.Components.PrimaryButton
import org.flyhigh.os.TOKEN

@Composable
fun HomeView(vm: HomeViewModel, navController: NavController) {
    LaunchedEffect(Unit) {
        println(TOKEN.userToken)
    }


    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .background(color = CSColors.backgroundColor)
                        .padding(20.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                    ,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    NavBar(vm, navController)

                    Image(modifier = Modifier.fillMaxSize(0.5f),painter = painterResource(resourcePath = "logo.png"), contentDescription = "logo", contentScale = ContentScale.Fit)

                    Text("Fly High", fontSize = 76.sp, fontWeight = FontWeight.Bold)
                }

                Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)) { }

            }

            SearchFieldView(vm, navController)
        }
    }
}



@Composable
fun SearchFieldView(vm: HomeViewModel, navController: NavController) {
    DefaultRowView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(start = 25.dp)
            .clip(RoundedCornerShape(size = 15.dp))
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(15.dp))
            .background(color = Color.LightGray)
    ) {
        TextFieldBoxes(
            text = vm.fromCity,
            title = "From",
            widthMin = 200.dp,
            widthMax = 250.dp,
            action = { str -> vm.updateFromCity(str) }
        )

        TextFieldBoxes(
            text = vm.toCity,
            title = "To",
            widthMin = 200.dp,
            widthMax = 250.dp,
            action = { str -> vm.updateToCity(str)}
        )

        WheelDatePickerBottomSheet(onDateSelected = { date ->
            vm.updateFlightDate(date)
        }, onlyFutureDates = true ,view = {
            TextFieldBoxes(text = vm.flightDate, title = "Departure", widthMin = 200.dp,
                widthMax = 250.dp, action = {}, readOnly = true)
        })

        WheelDatePickerBottomSheet(onDateSelected = { date ->
            vm.updateReturnDate(date)
        }, onlyFutureDates = true ,view = {
            TextFieldBoxes(text = vm.returnDate, title = "Return", widthMin = 200.dp,
                widthMax = 250.dp, action = {}, readOnly = true)
        })

        PassengersDropDownMenu(vm)

        PrimaryButton(text = "Search", textColor = Color.White, backgroundColor = Color.Black, onClick = {
            navController.navigate(route = "getFlights")
        })
    }
}


@Composable
fun NavBar(vm: HomeViewModel, navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Text("Past Orders",
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clickable {

                }
            ,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Text("Active Orders",
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clickable {

                }
            ,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Text("Profile",
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .clickable {
                    navController.navigate(route = "profilePage")
                }
            ,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}