package org.flyhigh.os.Components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PrimaryButton(text: String, backgroundColor: Color, textColor: Color, disabled: Boolean = false, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.wrapContentSize(),
        color = Color.Transparent
    ) {
        Text(text,
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = if (disabled) Color.DarkGray else backgroundColor)
                .padding(10.dp)
                .onClick {
                    if (!disabled) {
                        onClick()
                    }
                }
            ,
            color = textColor,
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}