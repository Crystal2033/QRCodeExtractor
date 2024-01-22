package com.crystal2033.qrextractor.ui.text_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldView(
    fieldHint: String,
    currentText: String,
    onValueChanged: (String) -> Unit = {},
    pattern: Regex = Regex("(\\w|\\d|\\s)*"),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    isPassword: Boolean = false,
    focusedColor: Color = Color.White,
    unfocusedColor: Color = Color.Black,
    isEnabled: Boolean = true


) {

//    val fieldValue = remember {
//        mutableStateOf("")
//    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        if (!isPassword) {
            OutlinedTextField(
                value = currentText,//fieldValue.value,
                label = { Text(fieldHint, color = focusedColor) },
                singleLine = true,
                placeholder = { Text(fieldHint) },
                onValueChange = {
                    if (it.matches(pattern)) {
                        //fieldValue.value = it
                        onValueChanged(it)
                    }

                },
                enabled = isEnabled,
                modifier = Modifier.align(verticalAlignment),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = focusedColor,
                    unfocusedBorderColor = unfocusedColor
                )
            )

        } else {
            var passwordVisible by rememberSaveable { mutableStateOf(false) }

            OutlinedTextField(
                value = currentText,//fieldValue.value,
                onValueChange = {
                    //fieldValue.value = it
                    onValueChanged(it)
                },
                enabled = isEnabled,
                label = { Text(fieldHint, color = focusedColor) },
                singleLine = true,
                placeholder = { Text(fieldHint) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = focusedColor,
                    unfocusedBorderColor = unfocusedColor
                )
            )
        }

    }


}

@Composable
@Preview
fun TextFieldPreview() {
//    val textToInsert = remember {
//        mutableStateOf("")
//    }
//    TextFieldView("Test string", textToInsert){
//
//    }
}