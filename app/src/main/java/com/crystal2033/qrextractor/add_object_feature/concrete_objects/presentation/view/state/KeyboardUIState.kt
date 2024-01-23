package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state

import androidx.compose.runtime.State
import com.crystal2033.qrextractor.core.remote_server.data.model.Keyboard

data class KeyboardUIState(
    override var deviceState: State<Keyboard>,
    override var isLoading: Boolean
) : BaseDeviceState<Keyboard> {
    override fun stateCopy(
        deviceState: State<Keyboard>,
        isLoading: Boolean
    ): BaseDeviceState<Keyboard> {
        return copy(deviceState = deviceState, isLoading = isLoading)
    }
}
