package com.crystal2033.qrextractor.auth_feature.domain.use_case

import com.crystal2033.qrextractor.auth_feature.data.dto.UserLoginDTO
import com.crystal2033.qrextractor.auth_feature.domain.repository.UserRepository
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.util.Resource
import kotlinx.coroutines.flow.Flow

class LoginUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(userLoginDTO: UserLoginDTO) : Flow<Resource<User?>> {
        return userRepository.tryLogin(userLoginDTO)
    }
}