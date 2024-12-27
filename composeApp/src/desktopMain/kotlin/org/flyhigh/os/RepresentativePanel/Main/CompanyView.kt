package org.flyhigh.os.RepresentativePanel.Main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.flyhigh.os.AuthViews.DefaultRowView
import org.flyhigh.os.AuthViews.TextFieldBoxes
import org.flyhigh.os.Components.BackButton

// Data Cla ss

@Serializable
data class AirlinesDataClass(
    val airline_id: String,
    val representative_id: String,
    var name: String,
    var logo: String,
    var contact_number: String,
    var contact_email: String,
    var address: String,
    var tin: String,
    var account_holder_name: String,
    var bank_name: String,
    var bank_swift_code: String,
    var bank_account_number: String,
    var airline_description: String,
    var pivot_pricing_for_economy: Double?,
    var pivot_pricing_for_comfort: Double?,
    var pivot_pricing_for_business: Double?,
    var pivot_pricing_for_first: Double?
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CompanyView(vm: RepresentativeHomeViewModel, navController: NavController) {

    LaunchedEffect(Unit) {
        vm.getAirlines()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            DefaultRowView {
                TextFieldBoxes(
                    text = vm.name,
                    title = "Airline Name",
                    action = { str -> vm.updateName(str) }
                )

                TextFieldBoxes(
                    text = vm.logo,
                    title = "Logo URL",
                    action = { str -> vm.updateLogo(str) }
                )
            }

            DefaultRowView {
                TextFieldBoxes(
                    text = vm.contactNumber,
                    title = "Contact Number",
                    action = { str -> vm.updateContactNumber(str) }
                )

                TextFieldBoxes(
                    text = vm.contactEmail,
                    title = "Contact Email",
                    action = { str -> vm.updateContactEmail(str) }
                )
            }

            DefaultRowView {
                TextFieldBoxes(
                    text = vm.address,
                    title = "Address",
                    action = { str -> vm.updateAddress(str) }
                )

                TextFieldBoxes(
                    text = vm.tin,
                    title = "TIN",
                    action = { str -> vm.updateTIN(str) }
                )
            }

            DefaultRowView {
                TextFieldBoxes(
                    text = vm.accountHolderName,
                    title = "Account Holder Name",
                    action = { str -> vm.updateAccountHolderName(str) }
                )

                TextFieldBoxes(
                    text = vm.bankName,
                    title = "Bank Name",
                    action = { str -> vm.updateBankName(str) }
                )
            }

            DefaultRowView {
                TextFieldBoxes(
                    text = vm.bankSwiftCode,
                    title = "Bank SWIFT Code",
                    action = { str -> vm.updateBankSwiftCode(str) }
                )

                TextFieldBoxes(
                    text = vm.bankAccountNumber,
                    title = "Bank Account Number",
                    action = { str -> vm.updateBankAccountNumber(str) }
                )
            }

            DefaultRowView {
                TextFieldBoxes(
                    text = vm.airlineDescription,
                    title = "Airline Description",
                    action = { str -> vm.updateAirlineDescription(str) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.popBackStack()
                },
//            enabled = isSaveEnabled,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }
        }


        BackButton(navController = navController)
    }
}
