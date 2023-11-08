package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory

import android.content.Context
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.QRCodeScannerUseCases

class UseCaseGetQRCodeFactory(
    private val qrCodeScannerUseCases: QRCodeScannerUseCases,
    private val applicationContext: Context
) {
    fun createUseCase(dataTableName: String) : GetDataFromQRCodeUseCase {
        return when(dataTableName){
            applicationContext.getString(R.string.person_table_name) -> {
                qrCodeScannerUseCases.getPersonFromQRCodeUseCase
            }
            applicationContext.getString(R.string.keyboard_table_name) ->  {
                qrCodeScannerUseCases.getKeyboardFromQRCodeUseCase
            }
//            "monitor" -> {
//
//            }
            else -> {
                throw ClassNotFoundException("Returning class for use case has not found.")
            }
        }
    }
}