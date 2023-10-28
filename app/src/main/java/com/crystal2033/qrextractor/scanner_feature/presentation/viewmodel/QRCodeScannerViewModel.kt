package com.crystal2033.qrextractor.scanner_feature.presentation.viewmodel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.core.scan_model.ScannedTableNameAndId
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.data.Converters
import com.crystal2033.qrextractor.scanner_feature.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.domain.model.Unknown
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.factory.GetDataFromQRCodeUseCase
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.factory.UseCaseGetQRCodeFactory
import com.crystal2033.qrextractor.scanner_feature.presentation.state.ScannedDataState
import com.crystal2033.qrextractor.scanner_feature.presentation.util.UIEvent
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRCodeScannerViewModel @Inject constructor(
    private val converter: Converters,
    private val useCaseGetQRCodeFactory: UseCaseGetQRCodeFactory
) : ViewModel() {
    companion object {
        const val timeForDuplicateQRCodesResistInMs = 12000L
    }

    private lateinit var getDataFromQRCodeUseCase: GetDataFromQRCodeUseCase

    private val _previewDataFromQRState = mutableStateOf(ScannedDataState())
    val previewDataFromQRState: State<ScannedDataState> = _previewDataFromQRState

    private val _listOfAddedScannables = mutableStateListOf<QRScannableData>()
    val listOfAddedScannables: SnapshotStateList<QRScannableData> = _listOfAddedScannables

    private val _eventFlow = Channel<UIEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private var scanJob: Job? = null

    private var deleteDuplicateQRCodeStringJob: Job? = null

    private var prevScanString: String? = null

    fun onScanQRCode(scanResult: String) {
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

    fun onEvent(event: QRScannerEvent){
        when(event){
            is QRScannerEvent.OnAddObjectInList -> {
                onAddScannableIntoListClicked(event.scannableObject, event.addEvenIfDuplicate)
            }
        }
    }

    private fun onAddScannableIntoListClicked(scannableObject: QRScannableData, isAddEvenDuplicate: Boolean){
        if (isAddEvenDuplicate){
            _listOfAddedScannables.add(scannableObject)
            return
        }

        if (_listOfAddedScannables.find { it == scannableObject} != null){
            viewModelScope.launch {
                _eventFlow.send(UIEvent.ShowDialogWindow(
                    message = "This object already exists in list. Do you really want to append another one?",
                    onDeclineAction = {},
                    onAcceptAction = {
                        onAddScannableIntoListClicked(scannableObject, true)
                    },
                    dialogTitle = "Duplicate object",
                    icon = Icons.Default.Warning
                ))
            }
        }
        else{
            _listOfAddedScannables.add(scannableObject)
        }
    }

    private fun showQRCodeFormatError(e: JsonSyntaxException) {
        Log.e("Convert error", e.message ?: "Unknown")
        scanJob = viewModelScope.launch {
            setStateInfo(Resource.Error(message = "QR-code`s content is not compatible with this application."))
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
                    Log.e("QR_ERROR", error.message ?: "Unknown error")
                    val errorMsg = error.message ?: "Unknown error"
                    setStateInfo(Resource.Error(message = errorMsg, Unknown(errorMsg)))
                    return@launch
                }
                getDataFromQRCodeUseCase(scannedObj.id).onEach { result ->
                    setStateInfo(result)
                }.launchIn(this)
            } ?: Log.e("QR_ERROR", "Error with scannedObject convertion. No id there")
        }
    }

    private suspend fun setStateInfo(data: Resource<QRScannableData>) {
        when (data) {
            is Resource.Loading -> {
                sendDataWithStatus(data, true)
            }

            is Resource.Error -> {
                setErrorStatus(null, data.message)
            }

            is Resource.Success -> {
                sendDataWithStatus(data, false)
            }
        }
    }

    private fun sendDataWithStatus(result: Resource<QRScannableData>, isLoading: Boolean) {
        _previewDataFromQRState.value = previewDataFromQRState.value.copy(
            scannedDataInfo = result.data,
            isLoading = isLoading
        )
    }

    private suspend fun setErrorStatus(result: Resource<QRScannableData>?, errorMessage: String?) {
        _previewDataFromQRState.value = previewDataFromQRState.value.copy(
            scannedDataInfo = result?.data,
            isLoading = false
        )
        _eventFlow.send(
            UIEvent.ShowSnackBar(
                message = errorMessage ?: "Unknown error"
            )
        )
    }
}