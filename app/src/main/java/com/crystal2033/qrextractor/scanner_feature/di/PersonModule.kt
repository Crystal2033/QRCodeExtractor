package com.crystal2033.qrextractor.scanner_feature.di

import android.content.Context
import com.crystal2033.qrextractor.scanner_feature.data.Converters
import com.crystal2033.qrextractor.scanner_feature.data.remote.api.PersonApi
import com.crystal2033.qrextractor.scanner_feature.data.repository.PersonRepositoryImpl
import com.crystal2033.qrextractor.scanner_feature.data.util.GsonParser
import com.crystal2033.qrextractor.scanner_feature.domain.repository.PersonRepository
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.GetPersonFromQRCodeUseCase
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.QRCodeScannerUseCases
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.factory.UseCaseGetQRCodeFactory
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersonModule {

    @Provides
    @Singleton
    fun provideQRCodeScannerUseCasesFactory(qrCodeScannerUseCases: QRCodeScannerUseCases,
                                            @ApplicationContext appContext : Context
    ) : UseCaseGetQRCodeFactory {
        return UseCaseGetQRCodeFactory(qrCodeScannerUseCases, appContext)
    }

    @Provides
    @Singleton
    fun provideQRCodeScannerUseCases(
        getPersonFromQRCodeUseCase: GetPersonFromQRCodeUseCase,
        ):QRCodeScannerUseCases{
        return QRCodeScannerUseCases(
            getPersonFromQRCodeUseCase,
            )
    }

//    @Provides
//    @Singleton
//    fun provideGetDataFromQRCodeUseCase(): GetDataFromQRCodeUseCase{
//        return GetDataFromQRCodeUseCase
//    }

    @Provides
    @Singleton
    fun provideGetPersonUseCase(repository: PersonRepository): GetPersonFromQRCodeUseCase{
        return GetPersonFromQRCodeUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePersonRepository(personApi: PersonApi
                                ): PersonRepository {
        return PersonRepositoryImpl(personApi)
    }

    @Provides
    @Singleton
    fun providePersonApi() : PersonApi{
        return Retrofit.Builder()
            .baseUrl(PersonApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PersonApi::class.java)
    }


    @Provides
    @Singleton
    fun provideConverter(): Converters {
        return Converters(GsonParser(Gson()))
    }


}