//package com.crystal2033.qrextractor.scanner_feature.scanned_info_list.di
//
//import com.crystal2033.qrextractor.core.localdb.AppDatabase
//import com.crystal2033.qrextractor.scanner_feature.general.repository_impl.ScannedGroupRepositoryImpl
//import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.repository.UserWithScannedGroupsRepository
//import com.crystal2033.qrextractor.scanner_feature.scanned_info_list.domain.use_case.GetListOfUserScannedGroupsUseCase
//import com.crystal2033.qrextractor.scanner_feature.scanner.domain.repository.KeyboardRepository
//import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.GetKeyboardFromQRCodeUseCase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object ScannedGroupModule {
//
//    @Provides
//    @Singleton
//    fun provideGetListOfUserScannedGroupsUseCase(repository: UserWithScannedGroupsRepository) : GetListOfUserScannedGroupsUseCase{
//        return GetListOfUserScannedGroupsUseCase(repository)
//    }
//
//}