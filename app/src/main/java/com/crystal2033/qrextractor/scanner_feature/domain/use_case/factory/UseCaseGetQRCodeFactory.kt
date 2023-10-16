package com.crystal2033.qrextractor.scanner_feature.domain.use_case.factory

import com.crystal2033.qrextractor.scanner_feature.domain.use_case.QRCodeScannerUseCases

class UseCaseGetQRCodeFactory(
   private val qrCodeScannerUseCases: QRCodeScannerUseCases
) {
    fun createUseCase(tableName: String) : GetDataFromQRCodeUseCase {
        return when(tableName){
            "person" -> {
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