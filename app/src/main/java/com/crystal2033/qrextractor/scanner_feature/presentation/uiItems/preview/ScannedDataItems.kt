package com.crystal2033.qrextractor.scanner_feature.presentation.uiItems.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.crystal2033.qrextractor.scanner_feature.domain.model.Keyboard
import com.crystal2033.qrextractor.scanner_feature.domain.model.Person
import com.crystal2033.qrextractor.scanner_feature.domain.model.QRScannableData
import com.crystal2033.qrextractor.scanner_feature.domain.model.Unknown

//@Language("AGSL")
//val CUSTOM_SHADER = """
//    uniform float2 resolution;
//    layout(color) uniform half4 color;
//    layout(color) uniform half4 color2;
//
//    half4 main(in float2 fragCoord) {
//        float2 uv = fragCoord/resolution.xy;
//
//        float mixValue = distance(uv, vec2(0, 1));
//        return mix(color, color2, mixValue);
//    }
//""".trimIndent()
//
//val BoxGreen = Color(0xFF31443E)
//val BoxDarkBlue = Color(0xFF484E64)

@Composable
fun ShowDataItemByType(
    qrScannable: QRScannableData?,
    modifier: Modifier = Modifier,
    onAddObjectIntoListClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color.Black
                    )
                )
            )
    ) {
        when (qrScannable) {
            is Unknown -> {
                UnknownItem(unknownDataInfo = qrScannable)
            }

            is Person -> {
                PersonInfo(person = qrScannable)
            }

            is Keyboard -> {
                KeyboardItem(keyboard = qrScannable)
            }
        }
        qrScannable?.let {
            Button(
                modifier = Modifier.align(Alignment.TopEnd), onClick = onAddObjectIntoListClicked
            ) {
                Text(text = "Add in list", fontWeight = FontWeight.Bold, fontSize = 10.sp)
            }
        }

    }

}