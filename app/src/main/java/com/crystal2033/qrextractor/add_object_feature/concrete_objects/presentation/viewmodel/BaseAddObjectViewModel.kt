package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.ScannedTableNameAndId

abstract class BaseAddObjectViewModel : ViewModel() {
    fun createQRCode(scannedTableNameAndId: ScannedTableNameAndId){
        //TODO: create qr code

    }
}