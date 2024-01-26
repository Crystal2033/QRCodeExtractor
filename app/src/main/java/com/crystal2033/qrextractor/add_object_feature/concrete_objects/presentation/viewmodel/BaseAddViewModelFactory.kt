package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle

abstract class BaseAddViewModelFactory {
    @Composable
    abstract fun createAddObjectViewModel(
        userAndPlaceBundle: UserAndPlaceBundle,
//        deviceForUpdate : InventarizedAndQRScannableModel?,
        navBackStackEntry: NavBackStackEntry,
        navController: NavController
    ): BaseAddObjectViewModel
}
