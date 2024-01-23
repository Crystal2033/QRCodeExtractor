package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.state

import androidx.compose.runtime.State
import com.crystal2033.qrextractor.core.remote_server.data.model.Projector

data class ProjectorUIState(
    override var deviceState: State<Projector>,
    override var isLoading: Boolean
) : BaseDeviceState<Projector> {
    override fun stateCopy(
        deviceState: State<Projector>,
        isLoading: Boolean
    ): BaseDeviceState<Projector> {
        return copy(deviceState = deviceState, isLoading = isLoading)
    }
}