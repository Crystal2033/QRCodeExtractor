package com.crystal2033.qrextractor.add_object_feature.place_choice.presentation.viewmodel

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface PlaceViewModelFactoryProvider {

    fun placeChoiceViewModelFactory(): PlaceChoiceViewModel.Factory
}