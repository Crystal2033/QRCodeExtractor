package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory

import android.content.Context
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.DataGetterUseCases

class UseCaseGetObjectFromServerFactory(
    private val getObjectInfoUseCases: DataGetterUseCases,
    private val context: Context
) {
    fun createUseCase(dataTableName: String) : GetScannableDataFromServerUseCase {
        return when(dataTableName){
            context.getString(R.string.person_table_name) -> {
                getObjectInfoUseCases.getPersonUseCase
            }
//            context.getString(R.string.chair_table_name) -> {
//                getObjectInfoUseCases.getChairUseCase
//            }
//            applicationContext.getString(R.string.keyboard_table_name) ->  {
//                getObjectInfoUseCases.getKeyboardUseCase
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