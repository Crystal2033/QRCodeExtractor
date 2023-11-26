package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.factory

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.core.model.User

abstract class BaseAddViewModelFactory {
    @Composable
    abstract fun createAddObjectViewModel(
        user: User,
        navBackStackEntry: NavBackStackEntry,
        navController: NavController
    ): BaseAddObjectViewModel
}
