//package com.crystal2033.qrextractor.core.di
//
//import android.content.Context
//import com.crystal2033.qrextractor.core.Routes
//import com.crystal2033.qrextractor.scanner_feature.domain.use_case.concrete_use_case.GetKeyboardFromQRCodeUseCase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object RoutesModule {
//    @Provides
//    @Singleton
//    fun provideRoutesContext(context: Context): Routes {
//        return Routes(context)
//    }
//}