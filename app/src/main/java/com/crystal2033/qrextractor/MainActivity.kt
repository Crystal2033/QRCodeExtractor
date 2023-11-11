package com.crystal2033.qrextractor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.crystal2033.qrextractor.core.User
import com.crystal2033.qrextractor.nav_graphs.addQRCodeGraph
import com.crystal2033.qrextractor.nav_graphs.documentsGraph
import com.crystal2033.qrextractor.nav_graphs.historyGraph
import com.crystal2033.qrextractor.nav_graphs.homeGraph
import com.crystal2033.qrextractor.nav_graphs.scannerGraph
import com.crystal2033.qrextractor.scanner_feature.general.di.ScannedDataViewModelFactoryProvider
import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.viewmodel.ScannedDataGroupsViewModel
import com.crystal2033.qrextractor.ui.NavBottomBar
import com.crystal2033.qrextractor.ui.theme.QRExtractorTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QRExtractorTheme(darkTheme = true) {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    bottomBar = {
                        NavBottomBar(navController, applicationContext)
                    }
                ) {
                    MyNavGraph(
                        navController = navController,
                        context = applicationContext,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }
}


@Composable
fun MyNavGraph(
    navController: NavHostController,
    context: Context,
    snackbarHostState: SnackbarHostState

) {

    val userViewModel: User by remember {
        mutableStateOf(User("123", 1))

    }
    NavHost(
        navController = navController,
        startDestination = context.resources.getString(R.string.home_head_graph_route)
    ) {
        //val userViewModel = User("some user", 1)
        homeGraph(navController, context, snackbarHostState)

        addQRCodeGraph(navController, context, snackbarHostState)

        scannerGraph(navController, context, snackbarHostState, userViewModel)

        historyGraph(navController, context, snackbarHostState)

        documentsGraph(navController, context, snackbarHostState)
    }
}

@Composable
fun TextWindow(string: String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = string,
            fontSize = 35.sp
        )
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

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

//@Composable
//fun scannedDataGroupsViewModel(user: User?): ScannedDataGroupsViewModel {
//    val factory = EntryPointAccessors.fromActivity(
//        LocalContext.current as Activity,
//        ScannedDataViewModelFactoryProvider::class.java
//    ).scannedDataGroupsViewModelFactory()
//
//    return viewModel(factory = ScannedDataGroupsViewModel.provideFactory(factory, user))
//}


