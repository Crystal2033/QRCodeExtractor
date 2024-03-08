package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.dialog_window

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.crystal2033.qrextractor.R


//TODO: what if group name already exist. Need to check the group name in database using viewmodel
@Composable
fun DialogInsertName(
    isNeedToShowDialog: MutableState<Boolean>,
    onAcceptButtonClicked: (String) -> Unit,
    title: String,
    helpMessage: String,
    placeholderInTextField: String,
    ifBlankErrorMessage: String = stringResource(id = R.string.field_cant_blank_translate)
) {
    var text by rememberSaveable { mutableStateOf("") }
    AlertDialog(
        icon = {
            //Icon(dialogWindowInfoState.icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = title)
        },
        text = {
            Column {
                Text(text = helpMessage)
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    placeholder = {
                        Text(text = placeholderInTextField)
                    },
                    isError = text.isBlank()
                )
                if (text.isBlank()) {
                    Text(
                        text = ifBlankErrorMessage,
                        color = Color.Red
                    )
                }
            }

        },
        onDismissRequest = {
            isNeedToShowDialog.value = false
        },
        confirmButton = {
            TextButton(
                enabled = text.isNotBlank(),
                onClick = {
                    isNeedToShowDialog.value = false
                    onAcceptButtonClicked(text)
                },
            ) {
                Text(stringResource(id = R.string.accept_translate))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    isNeedToShowDialog.value = false
                }
            ) {
                Text(stringResource(id = R.string.cancel_translate))
            }
        }
    )
}