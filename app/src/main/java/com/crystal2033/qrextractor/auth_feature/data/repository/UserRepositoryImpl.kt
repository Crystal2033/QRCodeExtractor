package com.crystal2033.qrextractor.auth_feature.data.repository

import android.content.Context
import android.util.Log
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.auth_feature.data.dto.UserLoginDTO
import com.crystal2033.qrextractor.auth_feature.data.remote.api.UserApi
import com.crystal2033.qrextractor.auth_feature.domain.repository.UserRepository
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val context: Context
) : UserRepository {
    override fun tryLogin(userLoginDTO: UserLoginDTO): Flow<Resource<User?>> = flow {
        emit(Resource.Loading())
        try {
            val user = tryToLoginUser(userLoginDTO, context)
            emit(Resource.Success(user))
        } catch (e: RemoteServerRequestException) {
            Log.e("ERROR", e.message ?: "Unknown error")
            emit(Resource.Error(message = e.message ?: "Unknown error"))
        }

    }

    private suspend fun tryToLoginUser(
        userLoginDTO: UserLoginDTO,
        context: Context
    ): User {
        val message: String
        try {
            val response = userApi.tryLogin(userLoginDTO)
            val user = response.body()?.toUser()
            user?.let {
                return user
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