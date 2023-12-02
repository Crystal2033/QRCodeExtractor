package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.vm_view_communication.UIAddNewObjectEvent
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.util.QRCodeGenerator
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.data.Converters
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.google.gson.JsonPrimitive
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

abstract class BaseAddObjectViewModel(
    private val context: Context,
    private val converter: Converters
) : ViewModel() {
    private val _eventFlow = Channel<UIAddNewObjectEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()
    fun createQRCode(qrScannableData: QRScannableData): ImageBitmap {
        val convertedJsonFromString = converter.toJsonFromQRScannableData(qrScannableData)
        val bitmap = QRCodeGenerator.encodeAsBitmap(convertedJsonFromString, 250, 250)
        return bitmap.asImageBitmap()
    }

    abstract fun addObjectInDatabaseClicked(onAddObjectClicked: (QRCodeStickerInfo) -> Unit)
    protected fun sendUiEvent(event: UIAddNewObjectEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }

    protected fun <T> insertPossibleObjectsInListIfSuccess(
        statusWithState: Resource<List<T>>,
        listOfPossibleObjects: MutableList<T>
    ) {
        when (statusWithState) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "Added list of possible objects")
                listOfPossibleObjects.addAll(statusWithState.data ?: emptyList())
                for (currentObj in listOfPossibleObjects) {
                    Log.i(LOG_TAG_NAMES.INFO_TAG, "Possible object: ${currentObj.toString()}")
                }
            }
        }
    }
}