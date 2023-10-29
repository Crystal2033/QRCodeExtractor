package com.crystal2033.qrextractor.scanner_feature.presentation.floating_views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.crystal2033.qrextractor.scanner_feature.presentation.state.DialogWindowInfoState

@Composable
fun DialogQuestion(
    isNeedToShowDialog: MutableState<Boolean>,
    dialogWindowInfoState: DialogWindowInfoState,
) {
    AlertDialog(
        icon = {
            Icon(dialogWindowInfoState.icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogWindowInfoState.dialogTitle)
        },
        text = {
            Text(text = dialogWindowInfoState.message)
        },
        onDismissRequest = {
            isNeedToShowDialog.value = false
            dialogWindowInfoState.onDeclineAction()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    isNeedToShowDialog.value = false
                    dialogWindowInfoState.onAcceptAction()
                }
            ) {
                Text("Accept")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    isNeedToShowDialog.value = false
                    dialogWindowInfoState.onDeclineAction()
                }
            ) {
                Text("Decline")
            }
        }
    )
}