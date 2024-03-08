package com.crystal2033.qrextractor.core.camera_for_photos

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun AcceptOrDeclinePhotoView(
    photo: Bitmap,
    onAcceptPhoto: (Bitmap) -> Unit,
    onDeclinePhoto: (Bitmap) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            bitmap = photo.asImageBitmap(),
            contentDescription = "Picture",
            modifier = Modifier
                .scale(1f)
                .align(Alignment.Center)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                onClick = {
                    onAcceptPhoto(photo)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Accept photo",
                    tint = Color.Green
                )

            }

            IconButton(
                onClick = {
                    onDeclinePhoto(photo)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Decline photo",
                    tint = Color.Red
                )

            }
        }
    }


}