package com.crystal2033.qrextractor.nav_graphs.scanner

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.core.User
import com.crystal2033.qrextractor.scanner_feature.general.di.ScannedDataViewModelFactoryProvider
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.viewmodel.ScannedDataGroupsViewModel
import dagger.hilt.android.EntryPointAccessors

sealed class ScannerViewModels(){
    companion object{
        @Composable
        inline fun <reified T : ViewModel> NavBackStackEntry.sharedScannedDataGroupsViewModel(
            navController: NavController,
            user: User
        ): T {
            val navGraphRoute = destination.parent?.route ?: return viewModel()
            val parentEntry = remember(this) {
                navController.getBackStackEntry(navGraphRoute)
            }

            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                ScannedDataViewModelFactoryProvider::class.java
            ).scannedDataGroupsViewModelFactory()

            return viewModel(
                viewModelStoreOwner = parentEntry,
                factory = ScannedDataGroupsViewModel.provideFactory(factory, user)
            )
        }
    }
}
