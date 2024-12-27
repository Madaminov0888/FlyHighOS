package org.flyhigh.os.AuthViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import org.example.project.AuthViews.TextFieldViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CursorDropDownMenu(vm: TextFieldViewModel) {
    var expanded by remember { mutableStateOf(false) } // Ensure expanded is true for the dropdown to appear

    Box(
        Modifier
            .background(color = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .onClick {
                    expanded = !expanded
                }
        ) {
            TextFieldBoxes(
                text = vm.citizenship,
                title = "Citizenship",
                action = {  },
                readOnly = true,
            )
        }


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            for (citizenship in vm.citizenships) {
                DropdownMenuItem(onClick = {
                    expanded = false
                    vm.updateCitizenship(citizenship)
                }) {
                    Text(citizenship)
                }
            }
        }
    }
}


@Composable
fun TextFieldBoxes(text: StateFlow<String>, title: String, action: (txt: String)->Unit, readOnly: Boolean = false, widthMin: Dp = 400.dp, widthMax: Dp = 450.dp) {
    MaterialTheme {
        val textValue by text.collectAsState()

        TextField(value = textValue, onValueChange = {newText -> action(newText)}, label = {
            Text(title)
        }, modifier = Modifier
            .padding()
            .widthIn(widthMin, widthMax)
            ,
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                backgroundColor = Color.White,
                disabledTextColor = Color.Black,
                disabledLabelColor = Color.DarkGray
            ),
            singleLine = true,
            readOnly = readOnly,
            enabled = !readOnly
        )
    }
}





@Composable
fun TextFieldBoxes2(text: String, title: String, action: (txt: String)->Unit, readOnly: Boolean = false, widthMin: Dp = 400.dp, widthMax: Dp = 450.dp) {
    MaterialTheme {
//        val textValue by text.collectAsState()

        TextField(value = text, onValueChange = {newText -> action(newText)}, label = {
            Text(title)
        }, modifier = Modifier
            .padding()
            .widthIn(widthMin, widthMax)
            ,
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                backgroundColor = Color.White,
                disabledTextColor = Color.Black,
                disabledLabelColor = Color.DarkGray
            ),
            singleLine = true,
            readOnly = readOnly,
            enabled = !readOnly
        )
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
fun WheelDatePickerBottomSheet(
    onDateSelected: (String) -> Unit,
    onlyFutureDates: Boolean = false, // Determines if only future dates are allowed
    view: @Composable () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    // Formatter to parse and format date strings
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    if (showDatePicker) {
        WheelDatePickerView(
            modifier = Modifier
                .widthIn(min = 300.dp, max = 500.dp)
                .padding(top = 22.dp, bottom = 26.dp),
            showDatePicker = showDatePicker,
            title = "Select a Date",
            doneLabel = "Select",
            titleStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
            ),
            doneLabelStyle = TextStyle(
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
            dateTextStyle = TextStyle(
                fontWeight = FontWeight(600),
            ),
            shape = RoundedCornerShape(18.dp),
            dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
            onDoneClick = { date ->
                // Parse the selected date
                val selectedLocalDate = LocalDate.parse(date.toString(), dateFormatter)
                val today = LocalDate.now()

                // Check if the date is valid based on the `onlyFutureDates` flag
                val isValidDate = if (onlyFutureDates) {
                    !selectedLocalDate.isBefore(today) // Future or today
                } else {
                    !selectedLocalDate.isAfter(today) // Past or today
                }

                if (isValidDate) {
                    selectedDate = selectedLocalDate.toString()
                    onDateSelected(selectedLocalDate.toString())
                    showDatePicker = false
                } else {
                    // Handle invalid date selection if necessary
                }
            },
            onDateChangeListener = { date ->
                val selectedLocalDate = LocalDate.parse(date.toString(), dateFormatter)
                val today = LocalDate.now()

                val isValidDate = if (onlyFutureDates) {
                    !selectedLocalDate.isBefore(today)
                } else {
                    !selectedLocalDate.isAfter(today)
                }

                if (isValidDate) {
                    selectedDate = selectedLocalDate.toString()
                    onDateSelected(selectedLocalDate.toString())
                }
            },
        )
    }

    Row(
        modifier = Modifier
            .onClick {
                showDatePicker = true
            }
            .background(color = Color.Transparent)
    ) {
        view()
    }
}