package com.crystal2033.qrextractor

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.crystal2033.qrextractor.ui.theme.QRExtractorTheme

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

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QRExtractorTheme {
                val navController = rememberNavController()
                val bottomItems = initBottomItems(applicationContext)


                var selectedItemIndex by rememberSaveable {
                    val homeItem: BottomNavigationItem? = bottomItems.find { bottomItem ->
                        bottomItem.mainRouteInGraph.lowercase() ==
                                resources.getString(R.string.home_head_graph_route).lowercase()
                    }
                    mutableIntStateOf(bottomItems.indexOf(homeItem))
                }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
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
                ) {
                    MyNavGraph(
                        navController = navController,
                        context = applicationContext
                    )
                }
            }
        }
    }
}


@Composable
fun MyNavGraph(
    navController: NavHostController,
    context: Context
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
                TextWindow("Scanner")
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

