package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state

import androidx.compose.runtime.State
import com.crystal2033.qrextractor.core.remote_server.data.model.Desk

data class DeskUIState(override var deviceState: State<Desk>, override var isLoading: Boolean) : BaseDeviceState<Desk> {
    override fun stateCopy(deviceState: State<Desk>, isLoading: Boolean): BaseDeviceState<Desk> {
        return copy(deviceState = deviceState, isLoading = isLoading)
    }
}
