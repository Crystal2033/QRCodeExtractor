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
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.util.stream.Collectors
import java.util.stream.Collectors.toList

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
                    chairResponse.body()?.stream()?.map(ChairDTO::toModel)?.collect(toList())
                chairs ?: throw RemoteServerRequestException(
                    ExceptionAndErrorParsers.getErrorMessageFromResponse(chairResponse)
                )
                chairs
            },
            apiCallFunction = { bundle, _ ->
                decoratorForGetAllChairs(
                    bundle
                )
            }
        )
    }

    override fun getDeviceById(deviceId: Long): Flow<Resource<Chair>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            deviceId
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
            apiCallFunction = { bundle, _ ->
                decoratorForGetChairById(
                    bundle
                )
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
                decoratorForAddChair(
                    bundle,
                    chairDTO
                )
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
                decoratorForUpdateChair(
                    bundle,
                    chairDTO
                )
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
            api = chairAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { },
            apiCallFunction = { bundle, _ ->
                decoratorForDeleteChair(
                    bundle
                )
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