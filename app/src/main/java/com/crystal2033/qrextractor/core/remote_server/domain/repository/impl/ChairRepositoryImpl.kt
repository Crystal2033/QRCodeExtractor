package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import com.crystal2033.qrextractor.core.remote_server.api.ChairAPI
import com.crystal2033.qrextractor.core.remote_server.data.APIArgumentsFillers
import com.crystal2033.qrextractor.core.remote_server.data.dto.ChairDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Chair
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.BundleID
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ChairRepository
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

class ChairRepositoryImpl(
    private val chairAPI: ChairAPI,
    @ApplicationContext private val context: Context
) : ChairRepository {
    override fun getAllDevicesInCabinet(cabinetId: Long): Flow<Resource<List<Chair>>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId,
            APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = chairAPI,
            context = context,
            bundleOfIds,
            null,
            getRequestBodyAndConvertInModel = { chairResponse ->
                val chairs =
                    chairResponse.body()?.stream()?.map(ChairDTO::toModel)?.toList()
                chairs ?: throw RemoteServerRequestException(
                    ExceptionAndErrorParsers.getErrorMessageFromResponse(chairResponse)
                )
                chairs
            },
            apiCallFunction = { bundle, deviceDTO ->
                var result: Response<List<ChairDTO>>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForGetAllChairs(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun getDeviceById(chairId: Long): Flow<Resource<Chair>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            chairId
        )

        return apiCall(
            api = chairAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { chairResponse ->
                val chair =
                    chairResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(chairResponse)
                    )
                chair
            },
            apiCallFunction = { bundle, deviceDTO ->
                var result: Response<ChairDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForGetChairById(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun addDevice(deviceDTO: ChairDTO?): Flow<Resource<Chair>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = chairAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,

            getRequestBodyAndConvertInModel = { chairResponse ->
                val chair =
                    chairResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(chairResponse)
                    )
                chair
            },
            apiCallFunction = { bundle, chairDTO ->
                var result: Response<ChairDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForAddChair(
                            bundle,
                            chairDTO
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun updateDevice(deviceDTO: ChairDTO): Flow<Resource<Chair>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = chairAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,
            getRequestBodyAndConvertInModel = { chairResponse ->
                val chair =
                    chairResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(chairResponse)
                    )
                chair
            },
            apiCallFunction = { bundle, chairDTO ->
                var result: Response<ChairDTO>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForUpdateChair(
                            bundle,
                            chairDTO
                        )
                    }
                }.wait()
                result!!
            }
        )
    }

    override fun deleteDevice(chairId: Long): Flow<Resource<Unit>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = chairId
        )

        return apiCall(
            api = chairAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { },
            apiCallFunction = { bundle, deviceDTO ->
                var result: Response<Unit>? = null
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.Main) {
                        result = decoratorForDeleteChair(
                            bundle
                        )
                    }
                }.wait()
                result!!
            }
        )
    }


    private suspend fun decoratorForGetAllChairs(
        bundleOfIds: BundleID
    ): Response<List<ChairDTO>> {

        return chairAPI.getAllChairsInCabinet(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            bundleOfIds.cabinetId
        )
    }

    private suspend fun decoratorForGetChairById(
        bundleOfIds: BundleID
    ): Response<ChairDTO> {

        return chairAPI.getChairById(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }

    private suspend fun decoratorForAddChair(
        bundleOfIds: BundleID,
        deviceDTO: ChairDTO?
    ): Response<ChairDTO> {

        return chairAPI.addChair(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForUpdateChair(
        bundleOfIds: BundleID,
        deviceDTO: ChairDTO?
    ): Response<ChairDTO> {

        return chairAPI.updateChair(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForDeleteChair(
        bundleOfIds: BundleID
    ): Response<Unit> {

        return chairAPI.deleteChair(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }

}