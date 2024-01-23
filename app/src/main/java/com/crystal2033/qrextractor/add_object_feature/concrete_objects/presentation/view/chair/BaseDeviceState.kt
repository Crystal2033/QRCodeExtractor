package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.view.chair

import androidx.compose.runtime.State

interface BaseDeviceState<M> {
    fun stateCopy(deviceState: State<M>, isLoading: Boolean): BaseDeviceState<M>

    var deviceState: State<M>
    var isLoading: Boolean
}