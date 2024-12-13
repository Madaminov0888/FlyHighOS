package org.example.project.AuthViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.utils.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterView(vm: TextFieldViewModel) {

    var showCitizenshipDropDown by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .padding(20.dp)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        DefaultRowView {
            TextFieldBoxes(
                text = vm.firstName,
                title = "First Name",
                action = {str -> vm.updateFirstName(str)}
            )

            TextFieldBoxes(
                text = vm.lastName,
                title = "Last Name",
                action = {str -> vm.updateLastName(str)}
            )
        }

        DefaultRowView {
            TextFieldBoxes(
                text = vm.email,
                title = "Email",
                action = {str -> vm.updateEmail(str)}
            )

            TextFieldBoxes(
                text = vm.password,
                title = "Password",
                action = {str -> vm.updatePassword(str)}
            )
        }

        DefaultRowView {
            TextFieldBoxes(
                text = vm.phoneNumber,
                title = "Phone Number",
                action = {str -> vm.updatePhoneNumber(str)}
            )

            WheelDatePickerBottomSheet(vm)
        }

        DefaultRowView {
            TextFieldBoxes(
                text = vm.foreignPassId,
                title = "Foreign Passport ID",
                action = { str -> vm.updateForeignPassId(str) }
            )

            TextFieldBoxes(
                text = vm.idCard,
                title = "ID card",
                action = { str -> vm.updateIdCard(str) }
            )
        }

        DefaultRowView(
            modifier = Modifier
                .onClick {
                    showCitizenshipDropDown = !showCitizenshipDropDown
                }
        ) {
            TextFieldBoxes(
                text = vm.citizenship,
                title = "Citizenship",
                action = {  },
                readOnly = true,
            )
        }
    }
}



@Composable
fun DefaultRowView(modifier: Modifier = Modifier,view: @Composable () -> Unit) {
    Row(modifier = modifier
        .padding(10.dp)
        .fillMaxWidth()
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround) {
        view()
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WheelDatePickerBottomSheet(vm: TextFieldViewModel) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    if (showDatePicker) {
        WheelDatePickerView(
            modifier = Modifier
                .widthIn(min = 300.dp, max = 500.dp)
                .padding(top = 22.dp, bottom = 26.dp),
            showDatePicker = showDatePicker,
            title = "Birth of Date",
            doneLabel = "Select",
            titleStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
            ),
            doneLabelStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF007AFF),
            ),
            dateTextColor = Color(0xff007AFF),
            selectorProperties = WheelPickerDefaults.selectorProperties(
                borderColor = Color.LightGray,
            ),
            rowCount = 5,
            height = 180.dp,
            dateTextStyle = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight(600),
            ),

            shape = RoundedCornerShape(18.dp),
            dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
            onDoneClick = {
                selectedDate = it.toString()
                vm.updateDateOfBirth(date = it.toString())
                showDatePicker = false
            },
            onDateChangeListener = {
                selectedDate = it.toString()
                vm.updateDateOfBirth(date = it.toString())
            },
//            onDismiss = {
//                showDatePicker = false
//            }
        )
    }

    Row(
        modifier = Modifier
            .onClick {
                showDatePicker = true
            }
            .background(color = Color.Transparent)
    ) {
        TextFieldBoxes(
            text = vm.dateOfBirth,
            title = "Date of birth",
            action = {},
            readOnly = true
        )
    }
}



