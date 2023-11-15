package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory

import android.content.Context
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.DataGetterUseCases

class UseCaseGetObjectFromServerFactory(
    private val getObjectInfoUseCases: DataGetterUseCases,
    private val applicationContext: Context
) {
    fun createUseCase(dataTableName: String) : GetDataFromServerUseCase {
        return when(dataTableName){
            applicationContext.getString(R.string.person_table_name) -> {
                getObjectInfoUseCases.getPersonUseCase
            }
            applicationContext.getString(R.string.keyboard_table_name) ->  {
                getObjectInfoUseCases.getKeyboardUseCase
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