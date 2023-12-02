package com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.add_object_feature.general.model.QRCodeStickerInfo
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.vm_view_communication.DocumentQRCodeStickersEvent

@Composable
fun QRCodeStickerView(
    qrCodeStickerInfo: QRCodeStickerInfo,
    onSizeChanged: (DocumentQRCodeStickersEvent) -> Unit
) {

    val sizes = listOf(
        QRCodeStickerInfo.StickerSize.SMALL,
        QRCodeStickerInfo.StickerSize.NORMAL,
        QRCodeStickerInfo.StickerSize.BIG
    )
    val isSelectedItem: (QRCodeStickerInfo.StickerSize) -> Boolean =
        {
            qrCodeStickerInfo.stickerSize == it

        }
    val onChangeState: (QRCodeStickerInfo.StickerSize) -> Unit =
        {
            onSizeChanged(
                DocumentQRCodeStickersEvent.OnChangeQRCodeStickerSize(
                    qrCodeStickerInfo,
                    it
                )
            )
        }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = qrCodeStickerInfo.essentialName
        )
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            bitmap = qrCodeStickerInfo.qrCode!!,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(qrCodeStickerInfo.stickerSize.sizeDp)
        )
        Text(
            text = qrCodeStickerInfo.inventoryNumber
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "QR-code size"
        )
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.size(100.dp)
//        ) {
        LazyRow() {
            items(sizes) { qrSize ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
//                        .selectable(
//                            selected = isSelectedItem(qrSize),
//                            onClick = { onChangeState(qrSize) },
//                            role = Role.RadioButton
//                        )

                ) {
                    RadioButton(
                        selected = isSelectedItem(qrSize),
                        onClick = { onChangeState(qrSize) }
                    )
                    Text(
                        text = qrSize.sizeDpString,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
        }
    }
}