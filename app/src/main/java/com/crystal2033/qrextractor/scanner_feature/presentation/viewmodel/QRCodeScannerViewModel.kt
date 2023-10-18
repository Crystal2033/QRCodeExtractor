package com.crystal2033.qrextractor.scanner_feature.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.data.Converters
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
    companion object{
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
        prevScanString = scanResult
        CoroutineScope(Dispatchers.Default).launch {
            delay(timeForDuplicateQRCodesResistInMs)
            prevScanString = ""
        }

        val scannedObject = converter.fromJsonToScannedTableNameAndId(scanResult)

        scanJob?.cancel()
        scanJob = viewModelScope.launch {
            scannedObject?.let { scannedObj ->
                //getPerson(scannedObj.id)
                getDataFromQRCodeUseCase = useCaseGetQRCodeFactory.createUseCase(scannedObj.tableName)
                getDataFromQRCodeUseCase(scannedObj.id)
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _previewDataFromQRState.value = previewDataFromQRState.value.copy(
                                    scannedDataInfo = result.data,
                                    isLoading = true
                                )
                            }

                            is Resource.Error -> {
                                _previewDataFromQRState.value = previewDataFromQRState.value.copy(
                                    scannedDataInfo = result.data,
                                    isLoading = false
                                )
                                _eventFlow.emit(
                                    UIEvent.ShowSnackBar(
                                        message = result.message ?: "Unknown error"
                                    )
                                )
                            }

                            is Resource.Success -> {
                                _previewDataFromQRState.value = previewDataFromQRState.value.copy(
                                    scannedDataInfo = result.data,
                                    isLoading = false
                                )

                                _eventFlow.emit(
                                    UIEvent.ShowSnackBar(
                                        message = result.data.toString()
                                    )
                                )
                            }
                        }
                    }.launchIn(this)
            } ?: Log.e("QR_TAG", "Error with scannedObject convertion. No id there")
        }
    }
}