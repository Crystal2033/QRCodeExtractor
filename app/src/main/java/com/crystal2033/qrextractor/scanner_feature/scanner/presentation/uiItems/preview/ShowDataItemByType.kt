package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.uiItems.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Unknown

@Composable
fun ShowDataItemByType(
    qrScannable: InventarizedAndQRScannableModel?,
    modifier: Modifier = Modifier,
    onAddObjectIntoListClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color.Black
                    )
                )
            )
    ) {
        qrScannable?.let {
            when (qrScannable) {
                is Unknown -> {
                    UnknownItem(unknownDataInfo = qrScannable)
                }

                else -> {
                    ScannedObjectPreview(
                        device = qrScannable,
                        cabinetName = "asd"
                    )
                }

//            is Person -> {
//                PersonInfo(person = qrScannable)
//            }

//            is Keyboard -> {
//                KeyboardItem(keyboard = qrScannable)
//            }
            }

            Button(
                modifier = Modifier.align(Alignment.TopEnd), onClick = onAddObjectIntoListClicked
            ) {
                Text(text = "Add in list", fontWeight = FontWeight.Bold, fontSize = 10.sp)
            }
        }

    }

}