package com.crystal2033.qrextractor.scanner_feature.domain.use_case.factory

import android.app.Application
import android.content.Context
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.domain.model.DatabaseObjectTypes
import com.crystal2033.qrextractor.scanner_feature.domain.use_case.QRCodeScannerUseCases
import dagger.hilt.android.qualifiers.ApplicationContext

class UseCaseGetQRCodeFactory(
   private val qrCodeScannerUseCases: QRCodeScannerUseCases,
    private val applicationContext: Context
) {
    fun createUseCase(dataTableName: String) : GetDataFromQRCodeUseCase {
        return when(dataTableName){
            applicationContext.getString(R.string.person_table_name) -> {
                qrCodeScannerUseCases.getPersonFromQRCodeUseCase
            }
//            "keyboard" ->  {
//
//            }
//            "monitor" -> {
//
//            }
            else -> {
                throw ClassNotFoundException("Returning class for use case has not found.")
            }
        }
    }
}