package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.monitor

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.AddDataViewModels.Companion.addMonitorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddViewModelFactory
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle

class AddMonitorViewModelFactory : BaseAddViewModelFactory() {
    @Composable
    override fun createAddObjectViewModel(
        userAndPlaceBundle: UserAndPlaceBundle,
        navBackStackEntry: NavBackStackEntry,
        navController: NavController
    ): BaseAddObjectViewModel {
        return addMonitorViewModel(userAndPlaceBundle)
    }
}