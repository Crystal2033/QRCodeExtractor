package com.crystal2033.qrextractor.scanner_feature.scanner

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.state.DialogWindowInfoState
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.UIScannerEvent


object StaticConverters {
    fun fromBytesIntoImageBitmap(stringBytes: String): ImageBitmap {
        val imageBytes = Base64.decode(stringBytes, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size).asImageBitmap()
    }

    fun fromEventDialogWindowIntoDialogInfoState(
        event: UIScannerEvent.ShowMessagedDialogWindow,
        dialogWindowState: DialogWindowInfoState
    ) {
        dialogWindowState.isNeedToShow = true
        dialogWindowState.dialogTitle = event.dialogTitle
        dialogWindowState.icon = event.icon
        dialogWindowState.message = event.message
        dialogWindowState.onAcceptAction = event.onAcceptAction
        dialogWindowState.onDeclineAction = event.onDeclineAction
    }
}