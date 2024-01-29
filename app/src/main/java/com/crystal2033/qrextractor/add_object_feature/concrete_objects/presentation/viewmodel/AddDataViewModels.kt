package com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.chair.AddChairViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.desk.AddDeskViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.keyboard.AddKeyboardViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.monitor.AddMonitorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.projector.AddProjectorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.system_unit.AddSystemUnitViewModel
import com.crystal2033.qrextractor.add_object_feature.general.di.AddDataViewModelFactoryProvider
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel.DocumentWithQRCodesViewModel
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.UserAndPlaceBundle
import dagger.hilt.android.EntryPointAccessors

sealed class AddDataViewModels {

    companion object {
        @Composable
        inline fun <reified T : ViewModel> NavBackStackEntry.sharedAddDataMenuViewModel(
            navController: NavController,
            userWithPlaceBundle: UserAndPlaceBundle
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
                factory = CreateQRCodesViewModel.provideFactory(factory, userWithPlaceBundle)
            )
        }

        @Composable
        inline fun <reified T : ViewModel> addChairViewModel(
            userAndPlaceBundle: UserAndPlaceBundle,
            chairForUpdate: InventarizedAndQRScannableModel?
        ): T {
            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                AddDataViewModelFactoryProvider::class.java
            ).addChairViewModelFactory()

            return viewModel(
                factory = AddChairViewModel.provideFactory(
                    factory,
                    userAndPlaceBundle,
                    chairForUpdate
                )
            )
        }

        @Composable
        inline fun <reified T : ViewModel> addDeskViewModel(
            userAndPlaceBundle: UserAndPlaceBundle,
            deskForUpdate: InventarizedAndQRScannableModel?
        ): T {
            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                AddDataViewModelFactoryProvider::class.java
            ).addDeskViewModelFactory()

            return viewModel(
                factory = AddDeskViewModel.provideFactory(
                    factory,
                    userAndPlaceBundle,
                    deskForUpdate
                )
            )
        }

        @Composable
        inline fun <reified T : ViewModel> addProjectorViewModel(
            userAndPlaceBundle: UserAndPlaceBundle,
            projectorForUpdate: InventarizedAndQRScannableModel?
        ): T {
            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                AddDataViewModelFactoryProvider::class.java
            ).addProjectorViewModelFactory()

            return viewModel(
                factory = AddProjectorViewModel.provideFactory(
                    factory,
                    userAndPlaceBundle,
                    projectorForUpdate
                )
            )
        }

        @Composable
        inline fun <reified T : ViewModel> addSystemUnitViewModel(
            userAndPlaceBundle: UserAndPlaceBundle,
            systemUnitForUpdate: InventarizedAndQRScannableModel?
        ): T {
            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                AddDataViewModelFactoryProvider::class.java
            ).addSystemUnitViewModelFactory()

            return viewModel(
                factory = AddSystemUnitViewModel.provideFactory(
                    factory,
                    userAndPlaceBundle,
                    systemUnitForUpdate
                )
            )
        }

        @Composable
        inline fun <reified T : ViewModel> addMonitorViewModel(
            userAndPlaceBundle: UserAndPlaceBundle,
            monitorForUpdate: InventarizedAndQRScannableModel?
        ): T {
            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                AddDataViewModelFactoryProvider::class.java
            ).addMonitorViewModelFactory()

            return viewModel(
                factory = AddMonitorViewModel.provideFactory(
                    factory,
                    userAndPlaceBundle,
                    monitorForUpdate
                )
            )
        }

        @Composable
        inline fun <reified T : ViewModel> addKeyboardViewModel(
            userAndPlaceBundle: UserAndPlaceBundle,
            keyboardForUpdate: InventarizedAndQRScannableModel?
        ): T {
            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                AddDataViewModelFactoryProvider::class.java
            ).addKeyboardViewModelFactory()

            return viewModel(
                factory = AddKeyboardViewModel.provideFactory(
                    factory,
                    userAndPlaceBundle,
                    keyboardForUpdate
                )
            )
        }

        @Composable
        inline fun <reified T : ViewModel> qrCodeDocumentViewModel(
            listOfQRCodes: SnapshotStateList<QRCodeStickerInfo>
        ): T {
            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                AddDataViewModelFactoryProvider::class.java
            ).documentQRCodesViewModelFactory()

            return viewModel(
                factory = DocumentWithQRCodesViewModel.provideFactory(factory, listOfQRCodes)
            )
        }

    }
}
