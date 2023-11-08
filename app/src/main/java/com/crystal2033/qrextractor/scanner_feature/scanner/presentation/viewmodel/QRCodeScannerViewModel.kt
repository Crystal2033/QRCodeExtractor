package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.User
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import com.crystal2033.qrextractor.scanner_feature.scanner.data.util.ScannedTableNameAndId
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.Unknown
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.InsertScannedGroupInDBUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.GetDataFromQRCodeUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.UseCaseGetQRCodeFactory
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.state.ScannedDataState
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.QRScannerEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.UIScannerEvent
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRCodeScannerViewModel @Inject constructor(
    private val converter: Converters,
    private val useCaseGetQRCodeFactory: UseCaseGetQRCodeFactory,
    private val insertScannedGroupInDBUseCase: InsertScannedGroupInDBUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    companion object {
        const val timeForDuplicateQRCodesResistInMs = 12000L
    }

    private lateinit var getDataFromQRCodeUseCase: GetDataFromQRCodeUseCase

    ///States
    private val _previewDataFromQRState = mutableStateOf(ScannedDataState())
    val previewDataFromQRState: State<ScannedDataState> = _previewDataFromQRState

    private val _listOfAddedScannables = mutableStateListOf<QRScannableData>()
    val listOfAddedScannables: SnapshotStateList<QRScannableData> = _listOfAddedScannables
    ///States

    private val _eventFlow = Channel<UIScannerEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private var scanJob: Job? = null

    private var deleteDuplicateQRCodeStringJob: Job? = null

    private var prevScanString: String? = null

    fun onEvent(event: QRScannerEvent) {
        when (event) {
            is QRScannerEvent.OnAddObjectInList -> {
                onAddScannableIntoListClicked(event.scannableObject, event.addEvenIfDuplicate)
            }

            is QRScannerEvent.OnAddScannedGroup -> {
                sendUiEvent(UIScannerEvent.ShowScannedGroupNameDialogWindow)
            }

            is QRScannerEvent.OnScanQRCode -> {
                onScanQRCode(event.scannedData)
            }

            is QRScannerEvent.OnAddNameForScannedGroup -> {
                onAddScannedGroupClicked(event.groupName)
                sendUiEvent(UIScannerEvent.Navigate(context.resources.getString(R.string.list_of_scanned_objects_route)))
            }

            is QRScannerEvent.ClearListOfScannedObjects -> {
                _listOfAddedScannables.clear()
            }
        }
    }


    private fun onAddScannedGroupClicked(groupName: String) {
        Log.i(LOG_TAG_NAMES.INFO_TAG, "Group name: $groupName")
        val TEST_USER = User("empty", 1)
        viewModelScope.launch {
            insertScannedGroupInDBUseCase(
                qrScannableDataList = listOfAddedScannables.toList(),
                user = TEST_USER,
                groupName = groupName
            ).onEach { result ->

            }.launchIn(this)
        }
    }

    private fun onScanQRCode(scanResult: String) {
        if (scanResult.isBlank() || prevScanString == scanResult) {
            return
        }
        setDeduplicateStringAndDelayForClear(scanResult)

        try {
            val scannedObject = converter.fromJsonToScannedTableNameAndId(scanResult)
            scanJob?.cancel()
            insertScannedDataInStateIfPossible(scannedObject)
        } catch (e: JsonSyntaxException) {
            showQRCodeFormatError(e)
        }

    }

    private fun onAddScannableIntoListClicked(
        scannableObject: QRScannableData,
        isAddEvenDuplicate: Boolean
    ) {
        if (isAddEvenDuplicate) {
            _listOfAddedScannables.add(scannableObject)
            return
        }

        if (_listOfAddedScannables.find { it == scannableObject } != null) {
            viewModelScope.launch {
                sendUiEvent(
                    UIScannerEvent.ShowMessagedDialogWindow(
                        message = "This object already exists in list. Do you really want to append another one?",
                        onDeclineAction = {},
                        onAcceptAction = {
                            onAddScannableIntoListClicked(scannableObject, true)
                        },
                        dialogTitle = "Duplicate object",
                        icon = Icons.Default.Warning
                    )
                )
            }
        } else {
            _listOfAddedScannables.add(scannableObject)
        }
    }

    private fun showQRCodeFormatError(e: JsonSyntaxException) {
        Log.e(LOG_TAG_NAMES.ERROR_TAG, e.message ?: "Unknown")
        scanJob = viewModelScope.launch {
            setPreviewObjectStateInfo(Resource.Error(message = "QR-code`s content is not compatible with this application."))
        }
    }

    private fun setDeduplicateStringAndDelayForClear(scanResult: String) {
        prevScanString = scanResult
        deleteDuplicateQRCodeStringJob?.cancel()
        deleteDuplicateQRCodeStringJob = CoroutineScope(Dispatchers.Default).launch {
            delay(timeForDuplicateQRCodesResistInMs)
            prevScanString = ""
        }
    }

    private fun insertScannedDataInStateIfPossible(scannedObject: ScannedTableNameAndId?) {
        scanJob = viewModelScope.launch {
            scannedObject?.let { scannedObj ->
                try {
                    getDataFromQRCodeUseCase =
                        useCaseGetQRCodeFactory.createUseCase(scannedObj.tableName)
                } catch (error: ClassNotFoundException) {
                    Log.e(LOG_TAG_NAMES.ERROR_TAG, error.message ?: "Unknown error")
                    val errorMsg = error.message ?: "Unknown error"
                    setPreviewObjectStateInfo(Resource.Error(message = errorMsg, Unknown(errorMsg)))
                    return@launch
                }

                getDataFromQRCodeUseCase(scannedObj.id).onEach { result ->
                    setPreviewObjectStateInfo(result)
                }.launchIn(this)
            } ?: Log.e(LOG_TAG_NAMES.ERROR_TAG, "Error with scannedObject convertion. No id there")
        }
    }

    private fun setPreviewObjectStateInfo(data: Resource<QRScannableData>) {
        when (data) {
            is Resource.Loading -> {
                sendDataWithStatus(data, true)
            }

            is Resource.Error -> {
                setErrorStatus(data.message)
            }

            is Resource.Success -> {
                sendDataWithStatus(data, false)
            }
        }
    }

    private fun setUIEventAfterAddedScannedGroup(data: Resource<Unit>) {
        when (data) {
            is Resource.Loading -> {

            }

            is Resource.Error -> {
            }

            is Resource.Success -> {
            }
        }
    }

    private fun sendDataWithStatus(result: Resource<QRScannableData>, isLoading: Boolean) {
        _previewDataFromQRState.value = previewDataFromQRState.value.copy(
            scannedDataInfo = result.data,
            isLoading = isLoading
        )
    }

    private fun setErrorStatus(errorMessage: String?) {
        _previewDataFromQRState.value = previewDataFromQRState.value.copy(
            scannedDataInfo = null,
            isLoading = false
        )

        sendUiEvent(
            UIScannerEvent.ShowSnackBar(
                message = errorMessage ?: "Unknown error"
            )
        )
    }

    private fun sendUiEvent(event: UIScannerEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}