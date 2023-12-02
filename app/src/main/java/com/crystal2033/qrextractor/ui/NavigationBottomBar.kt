package com.crystal2033.qrextractor.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.crystal2033.qrextractor.R

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
            .height(NavBottomBarConstants.HEIGHT_BOTTOM_BAR)
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
