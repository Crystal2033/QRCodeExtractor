package com.crystal2033.qrextractor.scanner_feature.presentation.viewmodel

import com.crystal2033.qrextractor.scanner_feature.data.Converters
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.GetPerson
import com.crystal2033.qrextractor.scanner_feature.presentation.util.UIEvent
import com.crystal2033.qrextractor.scanner_feature.presentation.state.PersonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    //private val jsonParser: GsonParser,
    private val converter: Converters,
    private val getPerson: GetPerson //TODO: make with any object
) : ViewModel() {
    private val _previewPersonState = mutableStateOf(PersonState())
    val previewPersonState: State<PersonState> = _previewPersonState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var scanJob: Job? = null

    fun onScanQRCode(scanResult: String) {
        val scannedObject = converter.fromScannedObjectsJson(scanResult)

        scanJob?.cancel()
        scanJob = viewModelScope.launch {
            scannedObject?.id?.let {
                getPerson(it)
                    .onEach { result->
                        when(result){
                            is Resource.Loading -> {
                                _previewPersonState.value = previewPersonState.value.copy(
                                    personInfo = result.data,
                                    isLoading = true
                                )
                            }
                            is Resource.Error -> {
                                _previewPersonState.value = previewPersonState.value.copy(
                                    personInfo = result.data,
                                    isLoading = false
                                )
                                _eventFlow.emit(
                                    UIEvent.ShowSnackBar(
                                        message = result.message ?: "Unknown error"
                                    )
                                )
                            }
                            is Resource.Success -> {
                                _previewPersonState.value = previewPersonState.value.copy(
                                    personInfo = result.data,
                                    isLoading = false
                                )
                            }
                        }
                    }.launchIn(this)
            } ?: Log.e("QR_TAG", "Error with scannedObject convertion. No id there")
        }
    }
}