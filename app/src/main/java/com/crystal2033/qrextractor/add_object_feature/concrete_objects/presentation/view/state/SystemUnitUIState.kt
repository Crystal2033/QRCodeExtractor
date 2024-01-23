package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state

import androidx.compose.runtime.State
import com.crystal2033.qrextractor.core.remote_server.data.model.SystemUnit

data class SystemUnitUIState(
    override var deviceState: State<SystemUnit>,
    override var isLoading: Boolean
) : BaseDeviceState<SystemUnit> {
    override fun stateCopy(
        deviceState: State<SystemUnit>,
        isLoading: Boolean
    ): BaseDeviceState<SystemUnit> {
        return copy(deviceState = deviceState, isLoading = isLoading)
    }
}
