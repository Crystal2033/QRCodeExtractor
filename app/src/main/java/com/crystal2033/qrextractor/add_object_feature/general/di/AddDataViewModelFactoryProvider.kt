package com.crystal2033.qrextractor.add_object_feature.general.di

import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.AddPersonViewModel
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface AddDataViewModelFactoryProvider {
    fun addDataMenuViewModelFactory() : CreateQRCodesViewModel.Factory
    fun addPersonViewModelFactory() : AddPersonViewModel.Factory
}