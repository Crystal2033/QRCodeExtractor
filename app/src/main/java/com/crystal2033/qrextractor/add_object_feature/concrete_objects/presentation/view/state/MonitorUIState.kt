package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state

import androidx.compose.runtime.State
import com.crystal2033.qrextractor.core.remote_server.data.model.Monitor

data class MonitorUIState(
    override var deviceState: State<Monitor>,
    override var isLoading: Boolean
) : BaseDeviceState<Monitor> {
    override fun stateCopy(
        deviceState: State<Monitor>,
        isLoading: Boolean
    ): BaseDeviceState<Monitor> {
        return copy(deviceState = deviceState, isLoading = isLoading)
    }
}
