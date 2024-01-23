package com.crystal2033.qrextractor.scanner_feature.scanner.di

import android.content.Context
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.DataGetterUseCases
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.GetPersonUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.UseCaseGetObjectFromServerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideQRCodeScannerUseCasesFactory(
        qrCodeScannerUseCases: DataGetterUseCases,
        @ApplicationContext context: Context
    ): UseCaseGetObjectFromServerFactory {
        return UseCaseGetObjectFromServerFactory(qrCodeScannerUseCases, context)
    }

    @Provides
    @Singleton
    fun provideQRCodeScannerUseCases(
        getPersonFromQRCodeUseCase: GetPersonUseCase,
        //getKeyboardFromQRCodeUseCase: GetKeyboardUseCase
    ): DataGetterUseCases {
        return DataGetterUseCases(
            getPersonFromQRCodeUseCase,
            //getKeyboardFromQRCodeUseCase
        )
    }
}