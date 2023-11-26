package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.factory

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.person.AddPersonViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.BaseAddObjectViewModel
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.nav_graphs.add_qr_data.AddDataViewModels.Companion.addPersonViewModel

class AddPersonViewModelFactory : BaseAddViewModelFactory() {
    @Composable
    override fun createAddObjectViewModel(
        user: User,
        navBackStackEntry: NavBackStackEntry,
        navController: NavController
    ): BaseAddObjectViewModel {
        return addPersonViewModel<AddPersonViewModel>(user)
    }
}