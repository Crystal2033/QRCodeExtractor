package com.crystal2033.qrextractor.core.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.crystal2033.qrextractor.R

@Composable
fun NotLoginLinkView(navController: NavController) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.login_system_translate),
            fontSize = 20.sp,
            color = Color.Cyan,
            modifier = Modifier
                .clickable {
                    navController.navigate(context.resources.getString(R.string.profile_head_graph_route))
                }
                .align(Alignment.Center)

        )
    }

}