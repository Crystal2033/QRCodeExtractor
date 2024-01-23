package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.projector

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddViewModelFactory
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.nav_graphs.add_qr_data.AddDataViewModels.Companion.addProjectorViewModel

class AddProjectorViewModelFactory : BaseAddViewModelFactory() {
    @Composable
    override fun createAddObjectViewModel(
        userAndPlaceBundle: UserAndPlaceBundle,
        navBackStackEntry: NavBackStackEntry,
        navController: NavController
    ): BaseAddObjectViewModel {
        return addProjectorViewModel<AddProjectorViewModel>(userAndPlaceBundle)
    }
}