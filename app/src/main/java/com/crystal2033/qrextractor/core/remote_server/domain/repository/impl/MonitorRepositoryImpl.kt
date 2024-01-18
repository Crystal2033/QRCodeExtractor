package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import com.crystal2033.qrextractor.core.remote_server.api.MonitorAPI
import com.crystal2033.qrextractor.core.remote_server.data.APIArgumentsFillers
import com.crystal2033.qrextractor.core.remote_server.data.dto.MonitorDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Monitor
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.BundleID
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.MonitorRepository
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import retrofit2.Response

class MonitorRepositoryImpl(
    private val monitorAPI: MonitorAPI,
    @ApplicationContext private val context: Context
) : MonitorRepository {


    override fun getAllDevicesInCabinet(cabinetId: Long): Flow<Resource<List<Monitor>>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId,
            APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = monitorAPI,
            context = context,
            bundleOfIds,
            null,
            getRequestBodyAndConvertInModel = { monitorResponse ->
                val monitors =
                    monitorResponse.body()?.stream()?.map(MonitorDTO::toModel)?.toList()
                monitors ?: throw RemoteServerRequestException(
                    ExceptionAndErrorParsers.getErrorMessageFromResponse(monitorResponse)
                )
                monitors
            },
            apiCallFunction = { bundle, monitorDTO ->
                var result: Response<List<MonitorDTO>>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForGetAllMonitors(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun getDeviceById(deviceId: Long): Flow<Resource<Monitor>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            deviceId
        )

        return apiCall(
            api = monitorAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { monitorResponse ->
                val monitor =
                    monitorResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(monitorResponse)
                    )
                monitor
            },
            apiCallFunction = { bundle, monitorDTO ->
                var result: Response<MonitorDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForGetMonitorById(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun addDevice(deviceDTO: MonitorDTO?): Flow<Resource<Monitor>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = monitorAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,

            getRequestBodyAndConvertInModel = { monitorResponse ->
                val monitor =
                    monitorResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(monitorResponse)
                    )
                monitor
            },
            apiCallFunction = { bundle, monitorDTO ->
                var result: Response<MonitorDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForAddMonitor(
                            bundle,
                            monitorDTO
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun updateDevice(deviceDTO: MonitorDTO): Flow<Resource<Monitor>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = monitorAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,
            getRequestBodyAndConvertInModel = { monitorResponse ->
                val monitor =
                    monitorResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(monitorResponse)
                    )
                monitor
            },
            apiCallFunction = { bundle, monitorDTO ->
                var result: Response<MonitorDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForUpdateMonitor(
                            bundle,
                            monitorDTO
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun deleteDevice(deviceId: Long): Flow<Resource<Unit>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = deviceId
        )

        return apiCall(
            api = monitorAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { },
            apiCallFunction = { bundle, deviceDTO ->
                var result: Response<Unit>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForDeleteMonitor(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }


    private suspend fun decoratorForGetAllMonitors(
        bundleOfIds: BundleID
    ): Response<List<MonitorDTO>> {

        return monitorAPI.getAllMonitorsInCabinet(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            bundleOfIds.cabinetId
        )
    }

    private suspend fun decoratorForGetMonitorById(
        bundleOfIds: BundleID
    ): Response<MonitorDTO> {

        return monitorAPI.getMonitorById(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }

    private suspend fun decoratorForAddMonitor(
        bundleOfIds: BundleID,
        deviceDTO: MonitorDTO?
    ): Response<MonitorDTO> {

        return monitorAPI.addMonitor(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForUpdateMonitor(
        bundleOfIds: BundleID,
        deviceDTO: MonitorDTO?
    ): Response<MonitorDTO> {

        return monitorAPI.updateMonitor(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForDeleteMonitor(
        bundleOfIds: BundleID
    ): Response<Unit> {

        return monitorAPI.deleteMonitor(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }


}