package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state

import androidx.compose.runtime.State
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel

interface BaseDeviceState {
    fun stateCopy(deviceState: State<InventarizedAndQRScannableModel>, isLoading: Boolean): BaseDeviceState

    var deviceState: State<InventarizedAndQRScannableModel>
    var isLoading: Boolean
}