package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.core.User
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.model.UserWithScannedGroups
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.use_case.GetListOfUserScannedGroupsUseCase
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.state.UserScannedGroupsState
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.vm_view_communication.ScannedDataListEvent
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.vm_view_communication.UIScannedDataListEvent
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.scanner.vm_view_communication.UIScannerEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannedDataGroupsViewModel @Inject constructor(
    private val getListOfUserScannedGroupsUseCase: GetListOfUserScannedGroupsUseCase
) : ViewModel() {

    var user: User? = null

    //states
    private val _scannedGroupsForUser = mutableStateOf(UserScannedGroupsState())
    val scannedGroupsForUser : State<UserScannedGroupsState> = _scannedGroupsForUser
    //states


    private val _eventFlow = Channel<UIScannedDataListEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    fun onEvent(event: ScannedDataListEvent) {
//        when (event) {
//
//        }
    }

    fun refresh(){
        user?.let {
            existingUser ->
            viewModelScope.launch {
                getListOfUserScannedGroupsUseCase(existingUser.id).onEach { result ->
                    setListDataInStates(result)
                }.launchIn(this)
            }
        }
    }

    private fun setListDataInStates(data: Resource<UserWithScannedGroups>){
        when (data) {
            is Resource.Loading -> {
                setDataWithStatus(data, true)
            }

            is Resource.Error -> {
                setErrorStatusAndSendSnackbarEvent(data.message)
            }

            is Resource.Success -> {
                setDataWithStatus(data, false)
            }
        }
    }

    private fun setDataWithStatus(result: Resource<UserWithScannedGroups>, isLoading: Boolean) {
        _scannedGroupsForUser.value = scannedGroupsForUser.value.copy(
            userScannedGroups = result.data,
            isLoading = isLoading
        )

    }

    private fun setErrorStatusAndSendSnackbarEvent(errorMessage: String?) {
        _scannedGroupsForUser.value = scannedGroupsForUser.value.copy(
            userScannedGroups = null,
            isLoading = false
        )

        sendUiEvent(
            UIScannedDataListEvent.ShowSnackBar(
                message = errorMessage ?: "Unknown error"
            )
        )
    }

    private fun sendUiEvent(event: UIScannedDataListEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}