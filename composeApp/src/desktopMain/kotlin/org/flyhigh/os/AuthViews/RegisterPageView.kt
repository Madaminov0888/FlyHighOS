package org.example.project.AuthViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.flyhigh.os.AuthViews.CursorDropDownMenu
import org.flyhigh.os.AuthViews.DefaultRowView
import org.flyhigh.os.AuthViews.TextFieldBoxes
import org.flyhigh.os.AuthViews.WheelDatePickerBottomSheet


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterView(vm: TextFieldViewModel) {

    var showCitizenshipDropDown by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        DefaultRowView {
            TextFieldBoxes(
                text = vm.firstName,
                title = "First Name",
                action = { str -> vm.updateFirstName(str)}
            )

            TextFieldBoxes(
                text = vm.lastName,
                title = "Last Name",
                action = { str -> vm.updateLastName(str)}
            )
        }

        DefaultRowView {
            TextFieldBoxes(
                text = vm.email,
                title = "Email",
                action = { str -> vm.updateEmail(str)}
            )

            TextFieldBoxes(
                text = vm.password,
                title = "Password",
                action = { str -> vm.updatePassword(str)}
            )
        }

        DefaultRowView {
            TextFieldBoxes(
                text = vm.phoneNumber,
                title = "Phone Number",
                action = { str -> vm.updatePhoneNumber(str)}
            )

            WheelDatePickerBottomSheet(onDateSelected = { date ->
                vm.updateDateOfBirth(date = date)
            }, view = {
                TextFieldBoxes(
                    text = vm.dateOfBirth,
                    title = "Date of birth",
                    action = {},
                    readOnly = true
                )
            })
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
            CursorDropDownMenu(vm)

            TextFieldBoxes(
                text = vm.country,
                title = "Country",
                action = { str -> vm.updateCountry(str) }
            )
        }

        DefaultRowView {
            TextFieldBoxes(
                text = vm.state,
                title = "State",
                action = { str -> vm.updateState(str) }
            )

            TextFieldBoxes(
                text = vm.city,
                title = "City",
                action = { str -> vm.updateCity(str) }
            )
        }

        DefaultRowView {
            TextFieldBoxes(
                text = vm.address,
                title = "Address",
                action = { str -> vm.updateAddress(str) }
            )

            TextFieldBoxes(
                text = vm.address2,
                title = "Second Address(Optional)",
                action = { str -> vm.updateAddress2(str) }
            )
        }
    }
}




