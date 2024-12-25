package org.flyhigh.os.Components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composables.core.Icon

@Composable
fun BackButton(navController: NavController) {
    Box {
        IconButton(
            onClick = { navController.popBackStack() }, // Handle back navigation
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .align(Alignment.TopStart)
                .clip(shape = CircleShape)
                .background(Color.White)
                .clipToBounds()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
    }
}