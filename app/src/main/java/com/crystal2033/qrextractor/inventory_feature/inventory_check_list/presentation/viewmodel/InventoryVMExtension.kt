package com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.viewmodel

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.data.inventarization.InventarizedINV_1FileParser
import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.di.InventoryCheckViewModelProvider
import dagger.hilt.android.EntryPointAccessors

class InventoryVMExtension {
    companion object {
        @Composable
        inline fun <reified T : ViewModel> NavBackStackEntry.inventoryViewModelWithFile(
            inventoryFile: InventarizedINV_1FileParser
        ): T {

            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                InventoryCheckViewModelProvider::class.java
            ).inventoryCheckViewModelFactory()

            return viewModel(
                factory = InventoryCheckViewModel.provideFactory(factory, inventoryFile)
            )
        }
    }
}