package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import com.crystal2033.qrextractor.core.remote_server.api.DeskAPI
import com.crystal2033.qrextractor.core.remote_server.data.APIArgumentsFillers
import com.crystal2033.qrextractor.core.remote_server.data.dto.DeskDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Desk
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.BundleID
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.DeskRepository
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

class DeskRepositoryImpl(
    private val deskAPI: DeskAPI,
    @ApplicationContext private val context: Context
) : DeskRepository {
    override fun getAllDevicesInCabinet(cabinetId: Long): Flow<Resource<List<Desk>>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId,
            APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = deskAPI,
            context = context,
            bundleOfIds,
            null,
            getRequestBodyAndConvertInModel = { deskResponse ->
                val desks =
                    deskResponse.body()?.stream()?.map(DeskDTO::toModel)?.toList()
                desks ?: throw RemoteServerRequestException(
                    ExceptionAndErrorParsers.getErrorMessageFromResponse(deskResponse)
                )
                desks
            },
            apiCallFunction = { bundle, deviceDTO ->
                var result: Response<List<DeskDTO>>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForGetAllDesks(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun getDeviceById(deviceId: Long): Flow<Resource<Desk>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            deviceId
        )

        return apiCall(
            api = deskAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { deskResponse ->
                val desk =
                    deskResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(deskResponse)
                    )
                desk
            },
            apiCallFunction = { bundle, deskDTO ->
                var result: Response<DeskDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForGetDeskById(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun addDevice(deviceDTO: DeskDTO?): Flow<Resource<Desk>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = deskAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,

            getRequestBodyAndConvertInModel = { deskResponse ->
                val desk =
                    deskResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(deskResponse)
                    )
                desk
            },
            apiCallFunction = { bundle, deskDTO ->
                var result: Response<DeskDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForAddDesk(
                            bundle,
                            deskDTO
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun updateDevice(deviceDTO: DeskDTO): Flow<Resource<Desk>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = deskAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,
            getRequestBodyAndConvertInModel = { deskResponse ->
                val desk =
                    deskResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(deskResponse)
                    )
                desk
            },
            apiCallFunction = { bundle, deskDTO ->
                var result: Response<DeskDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForUpdateDesk(
                            bundle,
                            deskDTO
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
            api = deskAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { },
            apiCallFunction = { bundle, deskDTO ->
                var result: Response<Unit>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForDeleteDesk(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    private suspend fun decoratorForGetAllDesks(
        bundleOfIds: BundleID
    ): Response<List<DeskDTO>> {

        return deskAPI.getAllDesksInCabinet(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            bundleOfIds.cabinetId
        )
    }

    private suspend fun decoratorForGetDeskById(
        bundleOfIds: BundleID
    ): Response<DeskDTO> {

        return deskAPI.getDeskById(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }

    private suspend fun decoratorForAddDesk(
        bundleOfIds: BundleID,
        deviceDTO: DeskDTO?
    ): Response<DeskDTO> {

        return deskAPI.addDesk(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForUpdateDesk(
        bundleOfIds: BundleID,
        deviceDTO: DeskDTO?
    ): Response<DeskDTO> {

        return deskAPI.updateDesk(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForDeleteDesk(
        bundleOfIds: BundleID
    ): Response<Unit> {

        return deskAPI.deleteDesk(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }
}