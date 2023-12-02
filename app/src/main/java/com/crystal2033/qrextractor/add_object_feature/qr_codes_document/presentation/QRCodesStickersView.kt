package com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel.DocumentWithQRCodesViewModel

@Composable
fun QRCodeStickersView(
    viewModel: DocumentWithQRCodesViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        items(viewModel.listOfQRCodesState) { qrSticker ->
            QRCodeStickerView(
                qrCodeStickerInfo = qrSticker,
                onSizeChanged = viewModel::onEvent
            )
        }
    }


}