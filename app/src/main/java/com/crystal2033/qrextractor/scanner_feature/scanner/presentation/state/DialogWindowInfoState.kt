package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

data class DialogWindowInfoState(
    var isNeedToShow: Boolean = false,
    var message: String = "Message",
    var onDeclineAction: () -> Unit = {},
    var onAcceptAction: () -> Unit = {},
    var dialogTitle: String = "Title",
    var icon: ImageVector = Icons.Default.Warning
)

