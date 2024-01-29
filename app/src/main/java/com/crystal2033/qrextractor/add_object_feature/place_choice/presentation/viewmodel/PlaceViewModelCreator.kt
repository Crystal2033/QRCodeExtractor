package com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.viewmodel

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.crystal2033.qrextractor.core.model.User
import dagger.hilt.android.EntryPointAccessors

sealed class PlaceViewModelCreator {
    companion object {
        @Composable
        inline fun <reified T : ViewModel> NavBackStackEntry.sharedPlaceChoiceViewModel(
            navController: NavController,
            user: User?,
            startNextRoute: String=""
        ): T {
            val navGraphRoute = destination.parent?.route ?: return viewModel()
            val parentEntry = remember(this) {
                navController.getBackStackEntry(navGraphRoute)
            }

            val factory = EntryPointAccessors.fromActivity(
                LocalContext.current as Activity,
                PlaceViewModelFactoryProvider::class.java
            ).placeChoiceViewModelFactory()

            return viewModel(
                viewModelStoreOwner = parentEntry,
                factory = PlaceChoiceViewModel.provideFactory(factory, user, startNextRoute)
            )
        }
    }
}