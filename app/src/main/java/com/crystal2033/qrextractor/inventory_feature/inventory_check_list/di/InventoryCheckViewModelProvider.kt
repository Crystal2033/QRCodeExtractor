package com.crystal2033.qrextractor.inventory_feature.inventory_check_list.di

import com.crystal2033.qrextractor.inventory_feature.inventory_check_list.presentation.viewmodel.InventoryCheckViewModel
import com.crystal2033.qrextractor.scanner_feature.scanner.presentation.viewmodel.QRCodeScannerViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface InventoryCheckViewModelProvider {

    fun inventoryCheckViewModelFactory(): InventoryCheckViewModel.Factory
}