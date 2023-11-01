package com.crystal2033.qrextractor.scanner_feature.presentation.dialog_window

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


//TODO: what if group name already exist. Need to check the group name in database using viewmodel
@Composable
fun DialogScannedGroupName(
    isNeedToShowDialog: MutableState<Boolean>,
    onConfirmButtonClicked: (String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }


    AlertDialog(
        icon = {
            //Icon(dialogWindowInfoState.icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = "Scanned objects group name")
        },
        text = {
            Column {
                Text(text = "Please set scanned group name for added objects. ")
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    placeholder = {
                        Text(text = "Group name")
                    },
                    isError = text.isBlank()
                )
                if (text.isBlank()) {
                    Text(
                        text = "Your group name should not be blank.",
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
                    onConfirmButtonClicked(text)
                }
            ) {
                Text("Accept")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    isNeedToShowDialog.value = false
                }
            ) {
                Text("Cancel")
            }
        }
    )
}