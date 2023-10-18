package com.crystal2033.qrextractor.scanner_feature.presentation.uiItems

import androidx.compose.runtime.Composable
import com.crystal2033.qrextractor.scanner_feature.domain.model.Person
import com.crystal2033.qrextractor.scanner_feature.domain.model.QRScannableData

@Composable
fun ShowDataItemByType(qrScannable: QRScannableData) {
    when (qrScannable){
        is Person ->{
            PersonInfo(qrScannable)
        }
    }
}