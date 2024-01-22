package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.factory

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.chair.AddChairViewModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.nav_graphs.add_qr_data.AddDataViewModels.Companion.addChairViewModel

class AddChairViewModelFactory : BaseAddViewModelFactory() {
    @Composable
    override fun createAddObjectViewModel(
        userAndPlaceBundle: UserAndPlaceBundle,
        navBackStackEntry: NavBackStackEntry,
        navController: NavController
    ): BaseAddObjectViewModel {
        return addChairViewModel<AddChairViewModel>(userAndPlaceBundle)
    }
}