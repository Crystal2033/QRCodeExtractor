package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetDeviceUseCaseInvoker
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.state.LoadStatusInfoState
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.vm_view_communication.FileLoaderEvent
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.vm_view_communication.UIFileLoaderEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.GetObjectFromServerUseCaseFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryFileLoaderViewModel @Inject constructor(
    private val useCaseGetQRCodeFactory: GetObjectFromServerUseCaseFactory
) : ViewModel() {
    private val _eventFlow = Channel<UIFileLoaderEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _fileLoadStatusInfo = mutableStateOf(LoadStatusInfoState())
    val fileLoadStatusInfo: State<LoadStatusInfoState> = _fileLoadStatusInfo

    private lateinit var getDataFromQRCodeUseCase: GetDeviceUseCaseInvoker
    fun onEvent(event: FileLoaderEvent) {
        when (event) {
            is FileLoaderEvent.SetFilePath -> {
                Log.i(LOG_TAG_NAMES.INFO_TAG, event.uri.toString())
            }
        }
    }

    private fun sendUiEvent(event: UIFileLoaderEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}