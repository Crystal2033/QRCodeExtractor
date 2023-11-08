package com.crystal2033.qrextractor.scanner_feature.scanner.di

import android.content.Context
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.QRCodeScannerUseCases
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.GetKeyboardFromQRCodeUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.concrete_use_case.GetPersonFromQRCodeUseCase
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory.UseCaseGetQRCodeFactory
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
    fun provideQRCodeScannerUseCasesFactory(qrCodeScannerUseCases: QRCodeScannerUseCases,
                                            @ApplicationContext context : Context
    ) : UseCaseGetQRCodeFactory {
        return UseCaseGetQRCodeFactory(qrCodeScannerUseCases, context)
    }

    @Provides
    @Singleton
    fun provideQRCodeScannerUseCases(
        getPersonFromQRCodeUseCase: GetPersonFromQRCodeUseCase,
        getKeyboardFromQRCodeUseCase: GetKeyboardFromQRCodeUseCase
    ): QRCodeScannerUseCases {
        return QRCodeScannerUseCases(
            getPersonFromQRCodeUseCase,
            getKeyboardFromQRCodeUseCase
        )
    }
}