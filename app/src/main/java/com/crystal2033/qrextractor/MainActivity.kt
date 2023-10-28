package com.crystal2033.qrextractor

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.crystal2033.qrextractor.scanner_feature.presentation.QRCodeView
import com.crystal2033.qrextractor.scanner_feature.presentation.viewmodel.QRCodeScannerViewModel
import com.crystal2033.qrextractor.ui.theme.QRExtractorTheme
import dagger.hilt.android.AndroidEntryPoint

data class BottomNavigationItem(
    val title: String,
    val mainRouteInGraph: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun initBottomItems(context: Context): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            title = "History",
            mainRouteInGraph = context.resources.getString(R.string.history_head_graph_route),
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_work_history_35),
            unselectedIcon = ImageVector.vectorResource(R.drawable.outline_work_history_35)
        ),
        BottomNavigationItem(
            title = "Add data",
            mainRouteInGraph = context.resources.getString(R.string.add_data_head_graph_route),
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_add_to_photos_35),
            unselectedIcon = ImageVector.vectorResource(R.drawable.outline_add_to_photos_35)
        ),
        BottomNavigationItem(
            title = "Home",
            mainRouteInGraph = context.resources.getString(R.string.home_head_graph_route),
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_home_35),
            unselectedIcon = ImageVector.vectorResource(R.drawable.outline_home_35)
        ),
        BottomNavigationItem(
            title = "Scanner",
            mainRouteInGraph = context.resources.getString(R.string.scanner_head_graph_route),
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_qr_code_35),
            unselectedIcon = ImageVector.vectorResource(R.drawable.outline_qr_code_35)
        ),
        BottomNavigationItem(
            title = "Documents",
            mainRouteInGraph = context.resources.getString(R.string.documents_head_graph_route),
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_description_35),
            unselectedIcon = ImageVector.vectorResource(R.drawable.outline_description_35)
        )
    )
}

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
fun NavBottomBar(navController: NavController, applicationContext: Context) {
    val bottomItems = initBottomItems(applicationContext)

    var selectedItemIndex by rememberSaveable {
        val homeItem: BottomNavigationItem? = bottomItems.find { bottomItem ->
            bottomItem.mainRouteInGraph.lowercase() ==
                    applicationContext.resources.getString(R.string.home_head_graph_route)
                        .lowercase()
        }
        mutableIntStateOf(bottomItems.indexOf(homeItem))
    }
    val gradientColors = listOf(
        Color.Black.copy(alpha = 0f),
        Color.Black.copy(alpha = 0.7f),
        Color.Black.copy(alpha = 0.9f),
        Color.Black
    )
    NavigationBar(
        modifier = Modifier
            .height(90.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors,
                )
            ),
        containerColor = Color.Transparent,
    ) {
        bottomItems.forEachIndexed { index, bottomItem ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    print(bottomItem.title)
                    navController.navigate(bottomItem.mainRouteInGraph)
                },
                label = {
                    Text(text = bottomItem.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.White,
                    indicatorColor = Color.DarkGray,
                    unselectedTextColor = Color.Gray
                ),
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            bottomItem.selectedIcon
                        } else bottomItem.unselectedIcon,
                        contentDescription = bottomItem.title
                    )
                })
        }

    }
}


@Composable
fun MyNavGraph(
    navController: NavHostController,
    context: Context,
    snackbarHostState: SnackbarHostState

) {
    NavHost(
        navController = navController,
        startDestination = context.resources.getString(R.string.home_head_graph_route)
    ) {
        navigation(
            startDestination = context.resources.getString(R.string.home_route),
            route = context.resources.getString(R.string.home_head_graph_route)
        ) {
            composable(context.resources.getString(R.string.home_route)) {
                TextWindow("Home")
            }
        }
        navigation(
            startDestination = context.resources.getString(R.string.add_data_route),
            route = context.resources.getString(R.string.add_data_head_graph_route)
        ) {
            composable(context.resources.getString(R.string.add_data_route)) {
                TextWindow("Add data")
            }
        }

        navigation(
            startDestination = context.resources.getString(R.string.scanner_route),
            route = context.resources.getString(R.string.scanner_head_graph_route)
        ) {
            composable(context.resources.getString(R.string.scanner_route)) {
                val viewModel = it.sharedViewModel<QRCodeScannerViewModel>(navController)
                QRCodeView(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize(),
                    snackbarHostState = snackbarHostState
                )
            }
        }

        navigation(
            startDestination = context.resources.getString(R.string.history_route),
            route = context.resources.getString(R.string.history_head_graph_route)
        ) {
            composable(context.resources.getString(R.string.history_route)) {
                TextWindow("History")
            }
        }

        navigation(
            startDestination = context.resources.getString(R.string.documents_route),
            route = context.resources.getString(R.string.documents_head_graph_route)
        ) {
            composable(context.resources.getString(R.string.documents_route)) {
                TextWindow("Documents")
            }
        }
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


