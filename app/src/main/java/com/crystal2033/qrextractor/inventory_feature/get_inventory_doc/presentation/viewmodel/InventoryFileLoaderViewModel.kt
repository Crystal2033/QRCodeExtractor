package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetDeviceUseCaseInvoker
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.LoadStatus
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization.InventarizedINV_1FileParser
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.state.LoadStatusInfoState
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.vm_view_communication.FileLoaderEvent
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.vm_view_communication.UIFileLoaderEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.GetObjectFromServerUseCaseFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.apache.poi.EncryptedDocumentException
import org.crystal2033.inventarization.exception.FileNotValidException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class InventoryFileLoaderViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _eventFlow = Channel<UIFileLoaderEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _fileLoadStatusInfo = mutableStateOf(LoadStatusInfoState())
    val fileLoadStatusInfo: State<LoadStatusInfoState> = _fileLoadStatusInfo

    val inventoryFile = InventarizedINV_1FileParser()

    private lateinit var getDataFromQRCodeUseCase: GetDeviceUseCaseInvoker
    fun onEvent(event: FileLoaderEvent) {
        when (event) {
            is FileLoaderEvent.SetFilePath -> {
                initFileLists(event.uri)
            }

            FileLoaderEvent.StartInventoryCheck -> {
                sendUiEvent(UIFileLoaderEvent.Navigate(
                    context.resources.getString(R.string.inventory_list_check_route)
                ))
            }
        }
    }

    private fun setNewFileLoadStatusInfo(loadStatus: LoadStatus, message: String) {
        _fileLoadStatusInfo.value = fileLoadStatusInfo.value.copy(
            loadStatus = loadStatus,
            message = message
        )
    }

    private fun initFileLists(uri: Uri) {
        viewModelScope.launch {

            try {
                context.contentResolver.openInputStream(uri).use { file ->
                    setNewFileLoadStatusInfo(LoadStatus.LOADING, "Loading file...")

                    inventoryFile.init(file!!, uri).join()

                    setNewFileLoadStatusInfo(
                        LoadStatus.SUCCESS,
                        "File has been loaded successfully."
                    )
                }

            } catch (e: IOException) {
                setNewFileLoadStatusInfo(
                    LoadStatus.ERROR_OPENING_FILE,
                    e.message ?: "Unknown error with IOException"
                )
                Log.i(LOG_TAG_NAMES.ERROR_TAG, "Error with opening file ${uri}: ${e.message}")
            } catch (e: FileNotValidException) {
                setNewFileLoadStatusInfo(
                    LoadStatus.ERROR_PARSING_FILE,
                    e.message ?: "Unknown error with FileNotValidException"
                )
                Log.i(LOG_TAG_NAMES.ERROR_TAG, "Error with file ${uri}: ${e.message}")
            } catch (e: EncryptedDocumentException) {
                setNewFileLoadStatusInfo(
                    LoadStatus.UNKNOWN_ERROR,
                    e.message ?: "Unknown error with EncryptedDocumentException"
                )
                Log.i(LOG_TAG_NAMES.ERROR_TAG, "Error with encryption file ${uri}: ${e.message}")
            }

            Log.i(LOG_TAG_NAMES.INFO_TAG, "Success")
        }
    }

    private fun sendUiEvent(event: UIFileLoaderEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}