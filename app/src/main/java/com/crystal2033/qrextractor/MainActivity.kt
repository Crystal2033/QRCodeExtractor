package com.crystal2033.qrextractor

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.crystal2033.qrextractor.ui.theme.QRExtractorTheme

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

//object MyAppIcons {
//    val historyQRCodesIcon = R.drawable.filled_work_history_24
//    val addQRCodeIcon =
//}

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            QRExtractorTheme {
                val navController = rememberNavController()
                val bottomItems = listOf(
                    BottomNavigationItem(
                        title = "Last scanned",
                        selectedIcon = ImageVector.vectorResource(R.drawable.filled_work_history_35),
                        unselectedIcon = ImageVector.vectorResource(R.drawable.outline_work_history_35)
                    ),
                    BottomNavigationItem(
                        title = "Add data",
                        selectedIcon = ImageVector.vectorResource(R.drawable.filled_add_to_photos_35),
                        unselectedIcon = ImageVector.vectorResource(R.drawable.outline_add_to_photos_35)
                    ),
                    BottomNavigationItem(
                        title = "Home",
                        selectedIcon = ImageVector.vectorResource(R.drawable.filled_home_35),
                        unselectedIcon = ImageVector.vectorResource(R.drawable.outline_home_35)
                    ),
                    BottomNavigationItem(
                        title = "Scanner",
                        selectedIcon = ImageVector.vectorResource(R.drawable.filled_qr_code_35),
                        unselectedIcon = ImageVector.vectorResource(R.drawable.outline_qr_code_35)
                    ),
                    BottomNavigationItem(
                        title = "Documents",
                        selectedIcon = ImageVector.vectorResource(R.drawable.filled_description_35),
                        unselectedIcon = ImageVector.vectorResource(R.drawable.outline_description_35)
                    )
                )
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                NavHost(navController = navController,
                    startDestination = ""){

                }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            bottomItems.forEachIndexed { index, bottomItem ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                              selectedItemIndex = index
                                    },
                                    modifier = Modifier.size(100.dp),
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex){
                                                bottomItem.selectedIcon
                                            }
                                            else bottomItem.unselectedIcon,
                                            contentDescription = bottomItem.title
                                        )
                                    })

                            }
                        }
                    }
                ) {

                }
            }
        }
    }
}
