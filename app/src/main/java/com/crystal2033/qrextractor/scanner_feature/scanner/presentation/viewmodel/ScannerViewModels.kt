package com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import com.crystal2033.qrextractor.scanner_feature.general.di.ScannedDataViewModelFactoryProvider
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.domain.model.ScannedGroup
import com.crystal2033.qrextractor.scanner_feature.list_of_groups.presentation.viewmodel.ScannedDataGroupsViewModel
import com.crystal2033.qrextractor.scanner_feature.scanned_objects_list.presentation.viewmodel.ScannedObjectsListViewModel
import com.crystal2033.qrextractor.scanner_feature.scanner.di.QRCodeViewModelFactoryProvider
import dagger.hilt.android.EntryPointAccessors

sealed class ScannerViewModels {
    companion object {
        @Composable
        inline fun <reified T : ViewModel> NavBackStackEntry.sharedScannedDataGroupsViewModel(
            navController: NavController,
            user: User?
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

        @Composable
        inline fun <reified T : ViewModel> NavBackStackEntry.qrCodeScannerViewModel(
            user: User
        ): T {

            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                QRCodeViewModelFactoryProvider::class.java
            ).qrCodeScannerViewModelFactory()

            return viewModel(
                factory = QRCodeScannerViewModel.provideFactory(factory, user)
            )
        }

        @Composable
        inline fun <reified T : ViewModel> scannedObjectsListInGroupViewModel(
            scannedGroup: ScannedGroup
        ): T {
//            val navGraphRoute = destination.parent?.route ?: return viewModel()
//            val parentEntry = remember(this) {
//                navController.getBackStackEntry(navGraphRoute)
//            }

            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                ScannedDataViewModelFactoryProvider::class.java
            ).scannedObjectsListViewModelFactory()

            return viewModel(
                factory = ScannedObjectsListViewModel.provideFactory(factory, scannedGroup)
            )
        }
    }
}
