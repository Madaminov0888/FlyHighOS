package org.flyhigh.os.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.flyhigh.os.AuthViews.TextFieldBoxes
import org.flyhigh.os.Components.CSColors



@Composable
fun PassengersDropDownMenu(vm: HomeViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val adultNumber = vm.adultNumber.collectAsState()
    val childrenNumber = vm.childrenNumber.collectAsState()
    val infantsNumber = vm.infantsNumber.collectAsState()
    val flightClass = vm.flightClass.collectAsState()

    Box(
        modifier = Modifier.background(color = Color.Transparent)
    ) {
        Box(
            modifier = Modifier.clickable { expanded = !expanded }
        ) {
            TextFieldBoxes(
                text = vm.flightClass,
                title = "${vm.getPassengersNumber()} passengers",
                widthMin = 200.dp,
                widthMax = 250.dp,
                action = {},
                readOnly = true
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .defaultMinSize(minWidth = 300.dp)
            ) {
                Text(
                    text = "Number of passengers",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                // Adults
                DropDownMenuBox(
                    label = "Adults",
                    subtitle = "12 years and older",
                    count = adultNumber.value,
                    onDecrement = { vm.updateAdultNumber(adultNumber.value - 1) },
                    onIncrement = { vm.updateAdultNumber(adultNumber.value + 1) },
                    isDecrementEnabled = adultNumber.value > 1,
                    isIncrementEnabled = adultNumber.value < 8
                )

                // Children
                DropDownMenuBox(
                    label = "Children",
                    subtitle = "2 to 12 years old",
                    count = childrenNumber.value,
                    onDecrement = { vm.updateChildrenNumber(childrenNumber.value - 1) },
                    onIncrement = { vm.updateChildrenNumber(childrenNumber.value + 1) },
                    isDecrementEnabled = childrenNumber.value > 0,
                    isIncrementEnabled = childrenNumber.value < 8
                )

                // Infants
                DropDownMenuBox(
                    label = "Infants",
                    subtitle = "Under 2 years old",
                    count = infantsNumber.value,
                    onDecrement = { vm.updateInfantsNumber(infantsNumber.value - 1) },
                    onIncrement = { vm.updateInfantsNumber(infantsNumber.value + 1) },
                    isDecrementEnabled = infantsNumber.value > 0,
                    isIncrementEnabled = infantsNumber.value < 8
                )

                Text(
                    text = "Class",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                for (fClass in vm.flightClasses) {
                    DropDownClassBox(vm = vm, label = fClass, chosen = (fClass == flightClass.value), onChoice = {})
                }
            }
        }
    }
}



@Composable
fun DropDownClassBox(vm: HomeViewModel, label: String, chosen: Boolean,onChoice: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontWeight = FontWeight.Normal, fontSize = 17.sp)

        Button(
            onClick = { if (!chosen) vm.updateFlightClass(label) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (chosen) CSColors.skyBlue else Color.LightGray
            ),
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp)
        ) {

        }
    }
}



@Composable
fun DropDownMenuBox(
    label: String,
    subtitle: String,
    count: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    isDecrementEnabled: Boolean,
    isIncrementEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(label, fontWeight = FontWeight.Medium, fontSize = 17.sp)
            Text(subtitle, fontWeight = FontWeight.Light, fontSize = 13.sp)
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            // "-" Button
            Button(
                onClick = { if (isDecrementEnabled) onDecrement() },
                enabled = isDecrementEnabled,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isDecrementEnabled) CSColors.skyBlue else Color.Gray
                ),
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "-",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Display Count
            Text(
                text = count.toString(),
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // "+" Button
            Button(
                onClick = { if (isIncrementEnabled) onIncrement() },
                enabled = isIncrementEnabled,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isIncrementEnabled) CSColors.skyBlue else Color.Gray
                ),
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

