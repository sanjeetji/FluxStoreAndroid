package com.flux.store.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    hint: String? = null,
    color: Color = MaterialTheme.colorScheme.primary,
    textSize: TextUnit = 16.sp,
    hasBorder: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    isBackgroundEnabled: Boolean = true,
    singleLine: Boolean = true,
    isError: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { hint?.let { Text(text = it) } },
        singleLine = singleLine,
        isError = isError,
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (hasBorder) Modifier.border(1.dp, color, shape = MaterialTheme.shapes.medium)
                else Modifier
            )
            .background(
                color = if (isBackgroundEnabled) backgroundColor else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = if (isError) MaterialTheme.colorScheme.error else color,
            unfocusedIndicatorColor = color,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = MaterialTheme.colorScheme.error
        ),
        textStyle = TextStyle(color = color, fontSize = textSize)
    )
}

@Composable
fun CustomTextView(
    text: String,
    textSize: TextUnit = 16.sp,
    color: Color = MaterialTheme.colorScheme.onSurface,
    fontWeight: FontWeight = FontWeight.Normal,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = textSize,
        color = color,
        fontWeight = fontWeight,
        modifier = modifier
    )
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = Color.White,
    borderColor: Color = Color.Transparent,
    isRounded: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor),
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (borderColor != Color.Transparent) Modifier.border(
                    1.dp,
                    borderColor,
                    shape = if (isRounded) MaterialTheme.shapes.medium else MaterialTheme.shapes.small
                )
                else Modifier
            ),
        shape = if (isRounded) MaterialTheme.shapes.medium else MaterialTheme.shapes.small
    ) {
        Text(text = text, color = textColor)
    }
}

