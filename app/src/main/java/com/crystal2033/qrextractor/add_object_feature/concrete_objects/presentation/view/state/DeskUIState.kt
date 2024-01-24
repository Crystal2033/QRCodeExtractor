package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state

import androidx.compose.runtime.State
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel

data class DeskUIState(
    override var deviceState: State<InventarizedAndQRScannableModel>,
    override var isLoading: Boolean
) : BaseDeviceState {
    override fun stateCopy(
        deviceState: State<InventarizedAndQRScannableModel>,
        isLoading: Boolean
    ): BaseDeviceState {
        return copy(deviceState = deviceState, isLoading = isLoading)
    }
}
