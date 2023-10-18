package com.crystal2033.qrextractor.scanner_feature.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.core.scan_model.ScannedTableNameAndId
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.data.Converters
import com.crystal2033.qrextractor.scanner_feature.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.domain.model.Unknown
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.factory.GetDataFromQRCodeUseCase
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.factory.UseCaseGetQRCodeFactory
//import com.crystal2033.qrextractor.scanner_feature.presentation.state.PersonState
import com.crystal2033.qrextractor.scanner_feature.presentation.state.ScannedDataState
import com.crystal2033.qrextractor.scanner_feature.presentation.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRCodeScannerViewModel @Inject constructor(
    private val converter: Converters,
    private val useCaseGetQRCodeFactory: UseCaseGetQRCodeFactory
) : ViewModel() {
    companion object {
        const val timeForDuplicateQRCodesResistInMs = 10000L
    }

    private lateinit var getDataFromQRCodeUseCase: GetDataFromQRCodeUseCase


    private val _previewDataFromQRState = mutableStateOf(ScannedDataState())
    val previewDataFromQRState: State<ScannedDataState> = _previewDataFromQRState


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var scanJob: Job? = null

    private var prevScanString: String? = null

    fun onScanQRCode(scanResult: String) {
        if (scanResult.isBlank() || prevScanString == scanResult) {
            return
        }
        setDeduplicateStringAndDelayForClear(scanResult)

        val scannedObject = converter.fromJsonToScannedTableNameAndId(scanResult)

        scanJob?.cancel()
        insertScannedDataInStateIfPossible(scannedObject)
    }

    private fun setDeduplicateStringAndDelayForClear(scanResult: String) {
        prevScanString = scanResult
        CoroutineScope(Dispatchers.Default).launch {
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
                    setErrorStatus(
                        Resource.Error(message = error.message ?: "Unknown error", Unknown()),
                        error.message ?: "Unknown error")
                    return@launch
                }
                getDataFromQRCodeUseCase(scannedObj.id)
                    .onEach { result ->
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
                setErrorStatus(data, data.message)
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
        _eventFlow.emit(
            UIEvent.ShowSnackBar(
                message = errorMessage ?: "Unknown error"
            )
        )
    }
}