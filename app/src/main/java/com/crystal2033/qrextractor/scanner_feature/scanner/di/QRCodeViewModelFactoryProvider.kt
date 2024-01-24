package com.crystal2033.qrextractor.scanner_feature.scanner.di

import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel.QRCodeScannerViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface QRCodeViewModelFactoryProvider {

    fun qrCodeScannerViewModelFactory(): QRCodeScannerViewModel.Factory
}