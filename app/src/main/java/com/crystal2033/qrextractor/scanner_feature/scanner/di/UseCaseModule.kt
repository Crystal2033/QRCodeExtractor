package com.crystal2033.qrextractor.scanner_feature.scanner.di

import android.content.Context
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.chair.GetChairUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.desk.GetDeskUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.keyboard.GetKeyboardUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.monitor.GetMonitorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.projector.GetProjectorUseCase
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.system_unit.GetSystemUnitUseCase
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
        getChairUseCase: GetChairUseCase,
        getDeskUseCase: GetDeskUseCase,
        getKeyboardUseCase: GetKeyboardUseCase,
        getMonitorUseCase: GetMonitorUseCase,
        getSystemUnitUseCase: GetSystemUnitUseCase,
        getProjectorUseCase: GetProjectorUseCase
    ): DataGetterUseCases {
        return DataGetterUseCases(
            getChairUseCase,
            getDeskUseCase,
            getProjectorUseCase,
            getMonitorUseCase,
            getSystemUnitUseCase,
            getKeyboardUseCase
        )
    }
}