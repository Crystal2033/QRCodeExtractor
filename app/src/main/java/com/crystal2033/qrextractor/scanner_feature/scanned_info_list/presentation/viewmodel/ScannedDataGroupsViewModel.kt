package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.use_case.GetListOfUserScannedGroupsUseCase
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.vm_view_communication.ScannedDataListEvent
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.vm_view_communication.UIScannedDataListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class ScannedDataGroupsViewModel @Inject constructor(
    private val getListOfUserScannedGroupsUseCase: GetListOfUserScannedGroupsUseCase
) : ViewModel() {


    //states

    //states

    init {

    }

    private val _eventFlow = Channel<UIScannedDataListEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    fun onEvent(event: ScannedDataListEvent) {
//        when (event) {
//
//        }
    }
}