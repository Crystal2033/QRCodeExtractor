package com.crystal2033.qrextractor.add_object_feature.general.di

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.person.AddPersonViewModel
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel.DocumentWithQRCodesViewModel
import com.crystal2033.qrextractor.auth_feature.presentation.viewmodel.ProfileViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface AddDataViewModelFactoryProvider {
    fun addDataMenuViewModelFactory() : CreateQRCodesViewModel.Factory
    fun documentQRCodesViewModelFactory() : DocumentWithQRCodesViewModel.Factory
    fun addPersonViewModelFactory() : AddPersonViewModel.Factory
}