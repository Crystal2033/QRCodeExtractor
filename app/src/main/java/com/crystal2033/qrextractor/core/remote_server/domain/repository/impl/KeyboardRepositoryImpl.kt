package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import com.crystal2033.qrextractor.core.remote_server.api.KeyboardAPI
import com.crystal2033.qrextractor.core.remote_server.data.APIArgumentsFillers
import com.crystal2033.qrextractor.core.remote_server.data.dto.KeyboardDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.InventarizedAndQRScannableModel
import com.crystal2033.qrextractor.core.remote_server.data.model.Keyboard
import com.crystal2033.qrextractor.core.remote_server.domain.repository.bundle.BundleID
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.KeyboardRepository
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.util.stream.Collectors.toList

class KeyboardRepositoryImpl(
    private val keyboardAPI: KeyboardAPI,
    @ApplicationContext private val context: Context
) : KeyboardRepository {
    override fun getAllDevicesInCabinet(cabinetId: Long): Flow<Resource<List<InventarizedAndQRScannableModel>>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId,
            APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = keyboardAPI,
            context = context,
            bundleOfIds,
            null,
            getRequestBodyAndConvertInModel = { keyboardResponse ->
                val keyboards =
                    keyboardResponse.body()?.stream()?.map(KeyboardDTO::toModel)?.collect(toList())
                keyboards ?: throw RemoteServerRequestException(
                    ExceptionAndErrorParsers.getErrorMessageFromResponse(keyboardResponse)
                )
                keyboards
            },
            apiCallFunction = { bundle, _ ->
                decoratorForGetAllKeyboards(
                    bundle
                )
            }
        )
    }

    override fun getDeviceById(deviceId: Long): Flow<Resource<InventarizedAndQRScannableModel>> {
        val bundleOfIds = BundleID(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            deviceId
        )

        return apiCall(
            api = keyboardAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { keyboardResponse ->
                val keyboard =
                    keyboardResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(keyboardResponse)
                    )
                keyboard
            },
            apiCallFunction = { bundle, _ ->
                decoratorForGetKeyboardById(
                    bundle
                )
            }
        )
    }

    override fun addDevice(deviceDTO: KeyboardDTO?): Flow<Resource<InventarizedAndQRScannableModel>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = keyboardAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,

            getRequestBodyAndConvertInModel = { keyboardResponse ->
                val keyboard =
                    keyboardResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(keyboardResponse)
                    )
                keyboard
            },
            apiCallFunction = { bundle, chairDTO ->
                decoratorForAddKeyboard(
                    bundle,
                    chairDTO
                )
            }
        )
    }

    override fun updateDevice(deviceDTO: KeyboardDTO): Flow<Resource<InventarizedAndQRScannableModel>> {
        val bundleOfIds = BundleID(
            orgId = APIArgumentsFillers.NOT_NEEDED.value,
            branchId = APIArgumentsFillers.NOT_NEEDED.value,
            buildingId = APIArgumentsFillers.NOT_NEEDED.value,
            cabinetId = APIArgumentsFillers.NOT_NEEDED.value,
            deviceId = APIArgumentsFillers.NOT_NEEDED.value
        )

        return apiCall(
            api = keyboardAPI,
            context = context,
            bundleOfIds,
            deviceDTO = deviceDTO,
            getRequestBodyAndConvertInModel = { keyboardResponse ->
                val keyboard =
                    keyboardResponse.body()?.toModel() ?: throw RemoteServerRequestException(
                        ExceptionAndErrorParsers.getErrorMessageFromResponse(keyboardResponse)
                    )
                keyboard
            },
            apiCallFunction = { bundle, chairDTO ->
                decoratorForUpdateKeyboard(
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
            api = keyboardAPI,
            context = context,
            bundleOfIds,
            deviceDTO = null,
            getRequestBodyAndConvertInModel = { },
            apiCallFunction = { bundle, _ ->
                decoratorForDeleteKeyboard(
                    bundle
                )
            }
        )
    }


    private suspend fun decoratorForGetAllKeyboards(
        bundleOfIds: BundleID
    ): Response<List<KeyboardDTO>> {

        return keyboardAPI.getAllKeyboardsInCabinet(
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            APIArgumentsFillers.NOT_NEEDED.value,
            bundleOfIds.cabinetId
        )
    }

    private suspend fun decoratorForGetKeyboardById(
        bundleOfIds: BundleID
    ): Response<KeyboardDTO> {

        return keyboardAPI.getKeyboardById(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }

    private suspend fun decoratorForAddKeyboard(
        bundleOfIds: BundleID,
        deviceDTO: KeyboardDTO?
    ): Response<KeyboardDTO> {
        return keyboardAPI.addKeyboard(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForUpdateKeyboard(
        bundleOfIds: BundleID,
        deviceDTO: KeyboardDTO?
    ): Response<KeyboardDTO> {

        return keyboardAPI.updateKeyboard(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            deviceDTO!!
        )
    }

    private suspend fun decoratorForDeleteKeyboard(
        bundleOfIds: BundleID
    ): Response<Unit> {

        return keyboardAPI.deleteKeyboard(
            bundleOfIds.orgId,
            bundleOfIds.branchId,
            bundleOfIds.buildingId,
            bundleOfIds.cabinetId,
            bundleOfIds.deviceId
        )
    }

}