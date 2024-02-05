package com.crystal2033.qrextractor.inventory_feature.get_inventory_doc.presentation.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

sealed class InventoryViewModelsInitializer {
    companion object {
        @Composable
        inline fun <reified T : ViewModel> NavBackStackEntry.sharedInventoryFileChoosingViewModel(
            navController: NavController
        ): T {
            val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
            val parentEntry = remember(this) {
                navController.getBackStackEntry(navGraphRoute)
            }

            return hiltViewModel(
                viewModelStoreOwner = parentEntry
            )
        }
    }
}