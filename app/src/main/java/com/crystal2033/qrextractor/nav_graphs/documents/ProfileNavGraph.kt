package com.crystal2033.qrextractor.nav_graphs.documents

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.State
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.auth_feature.presentation.LoginView
import com.crystal2033.qrextractor.auth_feature.presentation.viewmodel.ProfileViewModel
import com.crystal2033.qrextractor.auth_feature.presentation.viewmodel.factory.ProfileViewModelProvider.Companion.sharedProfileViewModel
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.User

fun NavGraphBuilder.profileGraph(
    navController: NavController,
    context: Context,
    onLoginUser: (User) -> Unit,
    stateUser: State<User?>,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = context.resources.getString(R.string.login_route),
        route = context.resources.getString(R.string.profile_head_graph_route)
    ) {
        composable(context.resources.getString(R.string.login_route)) { it ->

            val profileViewModel = it.sharedProfileViewModel<ProfileViewModel>(
                navController = navController,
                onLoginUser = onLoginUser
            )
            if(stateUser.value == null){
                LoginView(
                    viewModel = profileViewModel,
                    onNavigate = { event ->
                        navController.navigate(event.route)
                    }
                )
            }
            else{
                navController.navigate(context.resources.getString(R.string.profile_route))
            }

        }
        composable(context.resources.getString(R.string.profile_route)) {

            stateUser.value?.let { user ->
                Text(text = "Hello ${user.firstName} ${user.secondName} from ${user.organizationId} organization")
            }
        }
    }

}