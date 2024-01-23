package com.crystal2033.qrextractor.add_object_feature.general.di

import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.chair.AddChairViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.desk.AddDeskViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.projector.AddProjectorViewModel
import com.crystal2033.qrextractor.add_object_feature.concrete_objects.presentation.viewmodel.system_unit.AddSystemUnitViewModel
import com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel.CreateQRCodesViewModel
import com.crystal2033.qrextractor.add_object_feature.qr_codes_document.presentation.viewmodel.DocumentWithQRCodesViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface AddDataViewModelFactoryProvider {
    fun addDataMenuViewModelFactory(): CreateQRCodesViewModel.Factory
    fun documentQRCodesViewModelFactory(): DocumentWithQRCodesViewModel.Factory

    //fun addPersonViewModelFactory(): AddPersonViewModel.Factory
    fun addChairViewModelFactory(): AddChairViewModel.Factory

    fun addProjectorViewModelFactory(): AddProjectorViewModel.Factory

    fun addDeskViewModelFactory(): AddDeskViewModel.Factory

    fun addSystemUnitViewModelFactory(): AddSystemUnitViewModel.Factory


}