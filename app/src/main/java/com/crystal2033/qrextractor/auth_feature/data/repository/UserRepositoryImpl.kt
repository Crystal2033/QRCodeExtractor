package com.crystal2033.qrextractor.auth_feature.data.repository

import android.content.Context
import android.util.Log
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.auth_feature.data.dto.UserLoginDTO
import com.crystal2033.qrextractor.auth_feature.data.remote.api.UserApi
import com.crystal2033.qrextractor.auth_feature.domain.repository.UserRepository
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.localdb.UserDao
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.util.Resource
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.ExceptionAndErrorParsers
import com.crystal2033.qrextractor.scanner_feature.scanner.exceptions.RemoteServerRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.internal.wait
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val context: Context,
    private val userDao: UserDao
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
                insertOrUpdateLocalUserData(userLoginDTO, user)
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

    private suspend fun insertOrUpdateLocalUserData(
        userLoginDTO: UserLoginDTO,
        user: User
    ) {
        val userFromRoomDB = userDao.getUserByLogin(userLoginDTO.login)
        if (userFromRoomDB != null) {
            userDao.updateUser(user.toUserEntity())
            Log.i(LOG_TAG_NAMES.INFO_TAG, "User has updated in ROOM database.")
        } else {
            userDao.saveUser(user.toUserEntity())
            Log.i(LOG_TAG_NAMES.INFO_TAG, "User has been added in ROOM database.")
        }
    }
}