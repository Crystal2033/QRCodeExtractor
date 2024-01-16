package com.crystal2033.qrextractor.auth_feature.di

import com.crystal2033.qrextractor.auth_feature.presentation.viewmodel.ProfileViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ProfileViewModelFactoryProvider {

    fun profileViewModelFactory() : ProfileViewModel.Factory
}