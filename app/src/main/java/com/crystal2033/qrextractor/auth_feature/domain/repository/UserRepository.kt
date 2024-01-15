package com.crystal2033.qrextractor.auth_feature.domain.repository

import com.crystal2033.qrextractor.auth_feature.data.dto.UserLoginDTO
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun tryLogin(userLoginDTO: UserLoginDTO): Flow<Resource<User?>>
}