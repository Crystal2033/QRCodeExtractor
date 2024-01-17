package com.crystal2033.qrextractor.nav_graphs.home

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.camera_for_photos.CameraXView
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.ui.NavBottomBarConstants

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    context: Context,
    snackbarHostState: SnackbarHostState,
    userState: State<User?>
) {

    navigation(
        startDestination = context.resources.getString(R.string.home_route),
        route = context.resources.getString(R.string.home_head_graph_route)
    ) {
        composable(context.resources.getString(R.string.home_route)) {
            val image = remember {
                mutableStateOf<Bitmap?>(null)
            }
            Column(
                modifier = Modifier.padding(
                    0.dp,
                    0.dp,
                    0.dp,
                    NavBottomBarConstants.HEIGHT_BOTTOM_BAR
                )
            )
            {
                //TextWindow("Home")
                //CameraXView(darkTheme = true, image)
                //PickImageFromGallery()
//                image.value?.let { bitmap ->
//                    Image(
//                        bitmap = bitmap.asImageBitmap(),
//                        contentDescription = "Picture",
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(20.dp)
//                    )
//                }
            }
        }

    }
}

