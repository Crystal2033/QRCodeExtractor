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
import com.crystal2033.qrextractor.core.model.Person
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Unknown

@Composable
fun ShowDataItemByType(
    qrScannable: QRScannableData?,
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
        when (qrScannable) {
            is Unknown -> {
                UnknownItem(unknownDataInfo = qrScannable)
            }

            is Person -> {
                PersonInfo(person = qrScannable)
            }

//            is Keyboard -> {
//                KeyboardItem(keyboard = qrScannable)
//            }
        }
        qrScannable?.let {
            Button(
                modifier = Modifier.align(Alignment.TopEnd), onClick = onAddObjectIntoListClicked
            ) {
                Text(text = "Add in list", fontWeight = FontWeight.Bold, fontSize = 10.sp)
            }
        }

    }

}