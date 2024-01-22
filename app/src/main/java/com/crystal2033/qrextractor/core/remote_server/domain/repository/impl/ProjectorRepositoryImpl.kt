package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import com.crystal2033.qrextractor.core.remote_server.api.ProjectorAPI
import com.crystal2033.qrextractor.core.remote_server.data.APIArgumentsFillers
import com.crystal2033.qrextractor.core.remote_server.data.dto.ProjectorDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Projector
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.BundleID
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.ProjectorRepository
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ProjectorRepositoryImpl(
    private val projectorAPI: ProjectorAPI,
    @ApplicationContext private val context: Context
) : ProjectorRepository {


    override fun getAllDevicesInCabinet(cabinetId: Long): Flow<Resource<List<Projector>>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId,
            APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = projectorAPI,
            context = context,
            bundleOfIds,
            null,
            getRequestBodyAndConvertInModel = { projectorResponse ->
                val projectors =
                    projectorResponse.body()?.stream()?.map(ProjectorDTO::toModel)?.toList()
                projectors ?: throw RemoteServerRequestException(
                    ExceptionAndErrorParsers.getErrorMessageFromResponse(projectorResponse)
                )
                projectors
            },
            apiCallFunction = { bundle, projectorDTO ->
                decoratorForGetAllProjectors(
                    bundle
                )
            }
        )
    }

    override fun getDeviceById(deviceId: Long): Flow<Resource<Projector>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            deviceId
        )

        return apiCall(
            api = projectorAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { projectorResponse ->
                val projector =
                    projectorResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(projectorResponse)
                    )
                projector
            },
            apiCallFunction = { bundle, projectorDTO ->
                decoratorForGetProjectorById(
                    bundle
                )
            }
        )
    }

    override fun addDevice(deviceDTO: ProjectorDTO?): Flow<Resource<Projector>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = projectorAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,

            getRequestBodyAndConvertInModel = { projectorResponse ->
                val projector =
                    projectorResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(projectorResponse)
                    )
                projector
            },
            apiCallFunction = { bundle, projectorDTO ->
                decoratorForAddProjector(
                    bundle,
                    projectorDTO
                )
            }
        )
    }

    override fun updateDevice(deviceDTO: ProjectorDTO): Flow<Resource<Projector>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = projectorAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,
            getRequestBodyAndConvertInModel = { projectorResponse ->
                val projector =
                    projectorResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(projectorResponse)
                    )
                projector
            },
            apiCallFunction = { bundle, projectorDTO ->
                decoratorForUpdateProjector(
                    bundle,
                    projectorDTO
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
            api = projectorAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { },
            apiCallFunction = { bundle, deviceDTO ->
                decoratorForDeleteProjector(
                    bundle
                )
            }
        )
    }

    private suspend fun decoratorForGetAllProjectors(
        bundleOfIds: BundleID
    ): Response<List<ProjectorDTO>> {

        return projectorAPI.getAllProjectorsInCabinet(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            bundleOfIds.cabinetId
        )
    }

    private suspend fun decoratorForGetProjectorById(
        bundleOfIds: BundleID
    ): Response<ProjectorDTO> {

        return projectorAPI.getProjectorById(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }

    private suspend fun decoratorForAddProjector(
        bundleOfIds: BundleID,
        deviceDTO: ProjectorDTO?
    ): Response<ProjectorDTO> {

        return projectorAPI.addProjector(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForUpdateProjector(
        bundleOfIds: BundleID,
        deviceDTO: ProjectorDTO?
    ): Response<ProjectorDTO> {

        return projectorAPI.updateProjector(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForDeleteProjector(
        bundleOfIds: BundleID
    ): Response<Unit> {

        return projectorAPI.deleteProjector(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }


}