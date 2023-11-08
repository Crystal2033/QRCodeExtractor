//package com.crystal2033.qrextractor.scanner_feature.presentation.uiItems.preview
//
//import android.graphics.RuntimeShader
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawWithCache
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ShaderBrush
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import org.intellij.lang.annotations.Language
//
//
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
//val Coral = Color(0xFF31443E)
//val LightYellow = Color(0xFF484E64)
//
//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//@Composable
//fun EmptyBox(modifier: Modifier = Modifier, tableName: String = "Unknown") {
//    Box(
//        modifier = Modifier
//            .drawWithCache {
//                val shader = RuntimeShader(CUSTOM_SHADER)
//                val shaderBrush = ShaderBrush(shader)
//                shader.setFloatUniform("resolution", size.width, size.height)
//                onDrawBehind {
//                    shader.setColorUniform(
//                        "color",
//                        android.graphics.Color.valueOf(
//                            LightYellow.red, LightYellow.green,
//                            LightYellow
//                                .blue,
//                            LightYellow.alpha
//                        )
//                    )
//                    shader.setColorUniform(
//                        "color2",
//                        android.graphics.Color.valueOf(
//                            Coral.red,
//                            Coral.green,
//                            Coral.blue,
//                            Coral.alpha
//                        )
//                    )
//                    drawRect(shaderBrush)
//                }
//            }
//            .fillMaxWidth()
//            .height(200.dp)
//    ) {
//        Text(
//            text = tableName,
//            fontSize = 20.sp,
//            color = Color.Gray,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(15.dp)
//        )
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//@Composable
//@Preview
//fun EmptyItemPreview() {
//    EmptyBox()
//}