package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.crystal2033.qrextractor.core.remote_server.data.model.Chair
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel

data class ChairUIState(
    override var deviceState: State<InventarizedAndQRScannableModel> = mutableStateOf(Chair()),
    override var isLoading: Boolean = false
) : BaseDeviceState {
    override fun stateCopy(deviceState: State<InventarizedAndQRScannableModel>, isLoading: Boolean): BaseDeviceState {
        return copy(deviceState = deviceState, isLoading = isLoading)
    }
}