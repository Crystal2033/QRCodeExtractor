package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.projector

import androidx.compose.runtime.Composable
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels.Companion.addProjectorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle

class AddProjectorViewModelFactory {
    @Composable
    fun createAddObjectViewModel(
        userAndPlaceBundle: UserAndPlaceBundle,
        deviceForUpdate: InventarizedAndQRScannableModel?
    ): BaseAddObjectViewModel {
        return addProjectorViewModel(userAndPlaceBundle, deviceForUpdate)
    }
}