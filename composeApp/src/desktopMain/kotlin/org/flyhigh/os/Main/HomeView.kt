package org.flyhigh.os.Main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.flyhigh.os.AuthViews.DefaultRowView
import org.flyhigh.os.AuthViews.TextFieldBoxes
import org.flyhigh.os.AuthViews.WheelDatePickerBottomSheet
import org.flyhigh.os.Components.CSColors
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeView(vm: HomeViewModel, navController: NavController) {
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
                    Image(modifier = Modifier.fillMaxSize(0.5f),painter = painterResource(resourcePath = "logo.png"), contentDescription = "logo", contentScale = ContentScale.Fit)

                    Text("Fly High", fontSize = 76.sp, fontWeight = FontWeight.Bold)
                }

                Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)) { }

            }

            SearchFieldView(vm)
        }
    }
}



@Composable
fun SearchFieldView(vm: HomeViewModel) {
    DefaultRowView {
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
        }, view = {
            TextFieldBoxes(text = vm.flightDate, title = "Departure", widthMin = 200.dp,
                widthMax = 250.dp, action = {}, readOnly = true)
        })

        WheelDatePickerBottomSheet(onDateSelected = { date ->
            vm.updateReturnDate(date)
        }, view = {
            TextFieldBoxes(text = vm.returnDate, title = "Return", widthMin = 200.dp,
                widthMax = 250.dp, action = {}, readOnly = true)
        })



    }
}