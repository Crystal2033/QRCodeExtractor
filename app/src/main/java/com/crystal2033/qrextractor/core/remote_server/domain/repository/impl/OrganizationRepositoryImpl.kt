package com.crystal2033.qrextractor.core.remote_server.domain.repository.impl

import android.content.Context
import android.util.Log
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.core.remote_server.api.OrganizationAPI
import com.crystal2033.qrextractor.core.remote_server.data.dto.OrganizationDTO
import com.crystal2033.qrextractor.core.remote_server.data.model.Organization
import com.crystal2033.qrextractor.core.remote_server.domain.repository.interfaces.OrganizationRepository
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.stream.Collectors.toList

class OrganizationRepositoryImpl(
    private val organizationAPI: OrganizationAPI,
    private val context: Context
) : OrganizationRepository {
    override fun getOrganizations(): Flow<Resource<List<Organization>>> = flow {
        emit(Resource.Loading())
        try {
            val organizations = tryToGetOrganizations()
            emit(Resource.Success(organizations))
        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }

    override fun getOrganizationById(orgId: Long): Flow<Resource<Organization>> = flow {
        emit(Resource.Loading())
        try {
            val organization = tryToGetOrganization(orgId)
            emit(Resource.Success(organization))
        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }
    }

    private suspend fun tryToGetOrganizations(): List<Organization> {
        val message: String
        try {
            val response = organizationAPI.getAllOrganization()
            val organizations =
                response.body()?.stream()?.map(OrganizationDTO::toOrganization)?.collect(toList())
            organizations?.let {
                return it
            } ?: throw RemoteServerRequestException(
                ExceptionAndErrorParsers.getErrorMessageFromResponse(response)
            )
        } catch (e: HttpException) {
            message = ExceptionAndErrorParsers.getErrorMessageFromException(e)
            throw RemoteServerRequestException(message)
        } catch (e: IOException) {
            message = context.getString(R.string.server_connection_error)
            throw RemoteServerRequestException(message)
        }
    }

    private suspend fun tryToGetOrganization(orgId: Long): Organization {
        val message: String
        try {
            val response = organizationAPI.getOrganizationById(orgId)
            val organization =
                response.body()?.toOrganization()
            organization?.let {
                return it
            } ?: throw RemoteServerRequestException(
                ExceptionAndErrorParsers.getErrorMessageFromResponse(response)
            )
        } catch (e: HttpException) {
            message = ExceptionAndErrorParsers.getErrorMessageFromException(e)
            throw RemoteServerRequestException(message)
        } catch (e: IOException) {
            message = context.getString(R.string.server_connection_error)
            throw RemoteServerRequestException(message)
        }
    }
}