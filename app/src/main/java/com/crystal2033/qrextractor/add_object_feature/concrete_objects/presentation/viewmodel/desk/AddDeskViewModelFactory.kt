package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.desk

import androidx.compose.runtime.Composable
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels.Companion.addDeskViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle

class AddDeskViewModelFactory {
    @Composable
    fun createAddObjectViewModel(
        userAndPlaceBundle: UserAndPlaceBundle,
        deviceForUpdate: InventarizedAndQRScannableModel?
    ): BaseAddObjectViewModel {
        return addDeskViewModel(userAndPlaceBundle = userAndPlaceBundle, deviceForUpdate)
    }
}