package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory

import android.content.Context
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetDeviceUseCaseInvoker
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.DataGetterUseCases

class GetObjectFromServerUseCaseFactory(
    private val getObjectInfoUseCases: DataGetterUseCases,
    private val context: Context
) {
    fun createUseCase(dataTableName: String): GetDeviceUseCaseInvoker {
        return when (dataTableName) {
            context.getString(R.string.chair_table_name) -> {
                getObjectInfoUseCases.getChairUseCase
            }

            context.getString(R.string.desk_table_name) -> {
                getObjectInfoUseCases.getDeskUseCase
            }

            context.getString(R.string.monitor_table_name) -> {
                getObjectInfoUseCases.getMonitorUseCase
            }

            context.getString(R.string.projector_table_name) -> {
                getObjectInfoUseCases.getProjectorUseCase
            }

            context.getString(R.string.system_unit_table_name) -> {
                getObjectInfoUseCases.getSystemUnitUseCase
            }

            context.getString(R.string.keyboard_table_name) -> {
                getObjectInfoUseCases.getKeyboardUseCase
            }
            else -> {
                throw ClassNotFoundException("Returning class for use case has not found.")
            }
        }
    }
}