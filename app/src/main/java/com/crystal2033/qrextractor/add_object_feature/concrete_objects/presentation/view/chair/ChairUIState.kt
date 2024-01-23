package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.chair

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.crystal2033.qrextractor.core.remote_server.data.model.Chair

data class ChairUIState(
    override var deviceState: State<Chair> = mutableStateOf(Chair()),
    override var isLoading: Boolean = false
) : BaseDeviceState<Chair> {
    override fun stateCopy(deviceState: State<Chair>, isLoading: Boolean): BaseDeviceState<Chair> {
        return copy(deviceState = deviceState, isLoading = isLoading)
    }
}