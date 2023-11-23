package com.crystal2033.qrextractor.nav_graphs.home

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.add_object_feature.general.di.AddDataViewModelFactoryProvider
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import com.crystal2033.qrextractor.core.model.User
import dagger.hilt.android.EntryPointAccessors

sealed class AddDataViewModels {
    companion object {
        @Composable
        inline fun <reified T : ViewModel> NavBackStackEntry.sharedAddDataMenuViewModel(
            navController: NavController,
            user: User
        ): T {
            val navGraphRoute = destination.parent?.route ?: return viewModel()
            val parentEntry = remember(this) {
                navController.getBackStackEntry(navGraphRoute)
            }

            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                AddDataViewModelFactoryProvider::class.java
            ).addDataMenuViewModelFactory()

            return viewModel(
                viewModelStoreOwner = parentEntry,
                factory = CreateQRCodesViewModel.provideFactory(factory, user)
            )
        }
    }
}
