package com.crystal2033.qrextractor.scanner_feature.presentation.uiItems.preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.scanner_feature.domain.model.Keyboard

@Composable
fun KeyboardItem(keyboard: Keyboard?,
                 modifier: Modifier = Modifier
) {
    Surface(
        color = Color(0xff1c1b1f)
//        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "KEYBOARD",
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    keyboard?.image?.let { imageBitmap ->
                        Image(
                            bitmap = imageBitmap,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ShowId(
                        keyboard?.id ?: 0,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    FieldNameAndValue(
                        "Model",
                        "${keyboard?.model}"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FieldNameAndValue(
                        "Start use date",
                        keyboard?.startUseDate.toString()
                    )
                }

            }
        }


    }
}