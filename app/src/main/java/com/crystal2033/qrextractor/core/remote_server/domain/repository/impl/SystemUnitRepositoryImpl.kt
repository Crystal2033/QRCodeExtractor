package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import com.crystal2033.qrextractor.core.remote_server.api.SystemUnitAPI
import com.crystal2033.qrextractor.core.remote_server.data.APIArgumentsFillers
import com.crystal2033.qrextractor.core.remote_server.data.dto.SystemUnitDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.SystemUnit
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.BundleID
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.SystemUnitRepository
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

class SystemUnitRepositoryImpl(
    private val systemUnitAPI: SystemUnitAPI,
    @ApplicationContext private val context: Context
) : SystemUnitRepository {


    override fun getAllDevicesInCabinet(cabinetId: Long): Flow<Resource<List<SystemUnit>>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId,
            APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = systemUnitAPI,
            context = context,
            bundleOfIds,
            null,
            getRequestBodyAndConvertInModel = { systemUnitResponse ->
                val systemUnits =
                    systemUnitResponse.body()?.stream()?.map(SystemUnitDTO::toModel)?.toList()
                systemUnits ?: throw RemoteServerRequestException(
                    ExceptionAndErrorParsers.getErrorMessageFromResponse(systemUnitResponse)
                )
                systemUnits
            },
            apiCallFunction = { bundle, systemUnitDTO ->
                var result: Response<List<SystemUnitDTO>>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForGetAllSystemUnits(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun getDeviceById(deviceId: Long): Flow<Resource<SystemUnit>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            deviceId
        )

        return apiCall(
            api = systemUnitAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { systemUnitResponse ->
                val systemUnit =
                    systemUnitResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(systemUnitResponse)
                    )
                systemUnit
            },
            apiCallFunction = { bundle, systemUnitDTO ->
                var result: Response<SystemUnitDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForGetSystemUnitById(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun addDevice(deviceDTO: SystemUnitDTO?): Flow<Resource<SystemUnit>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = systemUnitAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,

            getRequestBodyAndConvertInModel = { systemUnitResponse ->
                val systemUnit =
                    systemUnitResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(systemUnitResponse)
                    )
                systemUnit
            },
            apiCallFunction = { bundle, systemUnitDTO ->
                var result: Response<SystemUnitDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForAddSystemUnit(
                            bundle,
                            systemUnitDTO
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun updateDevice(deviceDTO: SystemUnitDTO): Flow<Resource<SystemUnit>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = systemUnitAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,
            getRequestBodyAndConvertInModel = { systemUnitResponse ->
                val systemUnit =
                    systemUnitResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(systemUnitResponse)
                    )
                systemUnit
            },
            apiCallFunction = { bundle, systemUnitDTO ->
                var result: Response<SystemUnitDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForUpdateSystemUnit(
                            bundle,
                            systemUnitDTO
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
            api = systemUnitAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { },
            apiCallFunction = { bundle, deviceDTO ->
                var result: Response<Unit>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForDeleteSystemUnit(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    private suspend fun decoratorForGetAllSystemUnits(
        bundleOfIds: BundleID
    ): Response<List<SystemUnitDTO>> {

        return systemUnitAPI.getAllSystemUnitsInCabinet(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            bundleOfIds.cabinetId
        )
    }

    private suspend fun decoratorForGetSystemUnitById(
        bundleOfIds: BundleID
    ): Response<SystemUnitDTO> {

        return systemUnitAPI.getSystemUnitById(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }

    private suspend fun decoratorForAddSystemUnit(
        bundleOfIds: BundleID,
        deviceDTO: SystemUnitDTO?
    ): Response<SystemUnitDTO> {

        return systemUnitAPI.addSystemUnit(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForUpdateSystemUnit(
        bundleOfIds: BundleID,
        deviceDTO: SystemUnitDTO?
    ): Response<SystemUnitDTO> {

        return systemUnitAPI.updateSystemUnit(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForDeleteSystemUnit(
        bundleOfIds: BundleID
    ): Response<Unit> {

        return systemUnitAPI.deleteSystemUnit(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }


}