package com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.factory

import android.content.Context
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.DeleteDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.remote_server.domain.use_case.GetDeviceUseCaseInvoker
import com.crystal2033.qrextractor.core.util.GetStringNotInComposable
import com.crystal2033.qrextractor.scanner_feature.scanner.domain.use_case.DeleteObjectOnServerUseCases

class DeleteObjectOnServerUseCaseFactory(
    private val deleteObjectOnServerUseCases: DeleteObjectOnServerUseCases,
    private val context: Context
) {
    fun createUseCase(dataTableName: String): DeleteDeviceUseCaseInvoker {
        return when (dataTableName) {
            context.getString(R.string.chair_table_name) -> {
                deleteObjectOnServerUseCases.deleteChairUseCase
            }

            context.getString(R.string.desk_table_name) -> {
                deleteObjectOnServerUseCases.deleteDeskUseCase
            }

            context.getString(R.string.monitor_table_name) -> {
                deleteObjectOnServerUseCases.deleteMonitorUseCase
            }

            context.getString(R.string.projector_table_name) -> {
                deleteObjectOnServerUseCases.deleteProjectorUseCase
            }

            context.getString(R.string.system_unit_table_name) -> {
                deleteObjectOnServerUseCases.deleteSystemUnitUseCase
            }

            context.getString(R.string.keyboard_table_name) -> {
                deleteObjectOnServerUseCases.deleteKeyboardUseCase
            }

            else -> {
                throw ClassNotFoundException(GetStringNotInComposable(context, R.string.return_class_not_found_translate))
            }
        }
    }
}