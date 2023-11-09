package com.crystal2033.qrextractor.scanner_feature.general.di

import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.presentation.viewmodel.ScannedDataGroupsViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ScannedDataViewModelFactoryProvider {
    fun scannedDataGroupsViewModelFactory(): ScannedDataGroupsViewModel.Factory
}