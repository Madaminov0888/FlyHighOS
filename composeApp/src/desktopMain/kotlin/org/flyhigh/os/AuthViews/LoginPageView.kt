package org.example.project.AuthViews

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.flyhigh.os.AuthViews.TextFieldBoxes

@Composable
fun LoginPageView(vm: TextFieldViewModel) {
    Column(modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 40.dp),
        ) {
        TextFieldBoxes(text = vm.loginEmail, title = "Email", action = { newText -> vm.updateLoginEmail(newText) })

        TextFieldBoxes(text = vm.loginPassword, title = "Password", action = { newText -> vm.updateLoginPassword(newText) })
    }
}