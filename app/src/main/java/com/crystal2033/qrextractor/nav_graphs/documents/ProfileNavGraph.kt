package com.crystal2033.qrextractor.nav_graphs.documents

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.auth_feature.presentation.LoginView
import com.crystal2033.qrextractor.auth_feature.presentation.viewmodel.ProfileViewModel
import com.crystal2033.qrextractor.nav_graphs.ViewModelWithoutUserParameters.Companion.sharedHiltViewModel

fun NavGraphBuilder.profileGraph(
    navController: NavController,
    context: Context,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = context.resources.getString(R.string.login_route),
        route = context.resources.getString(R.string.profile_head_graph_route)
    ) {
        composable(context.resources.getString(R.string.login_route)) {
            val profileViewModel =
                it.sharedHiltViewModel<ProfileViewModel>(navController = navController)
            LoginView(
                viewModel = profileViewModel,
                onNavigate = { event ->
                    navController.navigate(event.route)
                }
            )
        }
        composable(context.resources.getString(R.string.profile_route)){
            val profileViewModel =
                it.sharedHiltViewModel<ProfileViewModel>(navController = navController)
            profileViewModel.user.value?.let {user ->
                Text(text = "Hello ${user.firstName} ${user.secondName} from ${user.organizationId} organization")
            }
        }
    }

}