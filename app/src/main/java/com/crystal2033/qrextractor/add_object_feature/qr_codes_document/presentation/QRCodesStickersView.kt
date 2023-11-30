package com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel.DocumentWithQRCodesViewModel

@Composable
fun QRCodeStickersView(
    viewModel: DocumentWithQRCodesViewModel
) {
    Text(text = "List size is: ${viewModel.getSizeOfList()}")
}