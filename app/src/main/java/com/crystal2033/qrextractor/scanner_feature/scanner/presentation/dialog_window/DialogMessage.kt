package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.dialog_window

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.state.DialogWindowInfoState

@Composable
fun DialogMessage(
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
                Text(stringResource(id = R.string.accept_translate))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    isNeedToShowDialog.value = false
                    dialogWindowInfoState.onDeclineAction()
                }
            ) {
                Text(stringResource(id = R.string.cancel_translate))
            }
        }
    )
}