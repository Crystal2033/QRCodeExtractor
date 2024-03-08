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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
            title = stringResource(id = R.string.inventory_translate),
            mainRouteInGraph = context.resources.getString(R.string.inventory_head_graph_route),
            selectedIcon = ImageVector.vectorResource(R.drawable.checklist_35),
            unselectedIcon = ImageVector.vectorResource(R.drawable.checklist_35)
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.add_data),
            mainRouteInGraph = context.resources.getString(R.string.add_data_head_graph_route),
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_add_to_photos_35),
            unselectedIcon = ImageVector.vectorResource(R.drawable.outline_add_to_photos_35)
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.home),
            mainRouteInGraph = context.resources.getString(R.string.home_head_graph_route),
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_home_35),
            unselectedIcon = ImageVector.vectorResource(R.drawable.outline_home_35)
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.scanner),
            mainRouteInGraph = context.resources.getString(R.string.scanner_head_graph_route),
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_qr_code_35),
            unselectedIcon = ImageVector.vectorResource(R.drawable.outline_qr_code_35)
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.profile),
            mainRouteInGraph = context.resources.getString(R.string.profile_head_graph_route),
            selectedIcon = ImageVector.vectorResource(R.drawable.filled_account_circle_35),
            unselectedIcon = ImageVector.vectorResource(R.drawable.outline_account_circle_35)
        )
    )
}

@Composable
fun NavBottomBar(navController: NavController, context: Context, startDestination: String) {
    val bottomItems = initBottomItems(context)

    var selectedItemIndex by remember {
        val homeItem: BottomNavigationItem? = bottomItems.find { bottomItem ->
            bottomItem.mainRouteInGraph.lowercase() ==
                    startDestination.lowercase()
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
