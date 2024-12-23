package org.flyhigh.os.RepresentativePanel.Auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.flyhigh.os.AuthViews.DefaultRowView
import org.flyhigh.os.AuthViews.TextFieldBoxes
import org.flyhigh.os.AuthViews.WheelDatePickerBottomSheet


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepresentativeRegisterPage(vm: RepresentativeAuthViewModel) {

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
                title = "Email of Representative",
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
                title = "Contact Number",
                action = { str -> vm.updatePhoneNumber(str) }
            )

            TextFieldBoxes(
                text = vm.companyName,
                title = "Company Name",
                action = { str -> vm.updateCompanyName(str) }
            )
        }

        DefaultRowView {
            TextFieldBoxes(
                text = vm.logoVar,
                title = "Upload Logo",
                action = {},
                readOnly = true,
            )

            TextFieldBoxes(
                text = vm.companyAddress,
                title = "Company Address",
                action = { str -> vm.updateCompanyAddress(str) }
            )
        }

        DefaultRowView {
            TextFieldBoxes(
                text = vm.tin,
                title = "TIN",
                action = { str -> vm.updateTin(str) }
            )

            WheelDatePickerBottomSheet(onDateSelected = { date ->
                vm.updateExpirationDate(date = date)
            }, view = {
                TextFieldBoxes(
                    text = vm.expirationDate,
                    title = "Expiration Date",
                    action = {},
                    readOnly = true
                )
            })
        }

        DefaultRowView {
            TextFieldBoxes(
                text = vm.holderName,
                title = "Holder name",
                action = { str -> vm.updateHolderName(str) }
            )

            TextFieldBoxes(
                text = vm.bankName,
                title = "Bank name",
                action = { str -> vm.updateBankName(str) }
            )
        }

        DefaultRowView {
            TextFieldBoxes(
                text = vm.bankAccountNumber,
                title = "Bank Account number",
                action = { str -> vm.updateBankAccountNumber(str) }
            )

            TextFieldBoxes(
                text = vm.SWIFTcode,
                title = "SWIFT code(Optional)",
                action = { str -> vm.updateSWIFTcode(str) }
            )
        }
    }
}



@Composable
fun RepresentativeLoginPage(vm: RepresentativeAuthViewModel) {
    Column(modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 40.dp),
    ) {
        TextFieldBoxes(text = vm.loginEmail, title = "Email", action = { newText -> vm.updateLoginEmail(newText) })

        TextFieldBoxes(text = vm.loginPassword, title = "Password", action = { newText -> vm.updateLoginPassword(newText) })
    }
}