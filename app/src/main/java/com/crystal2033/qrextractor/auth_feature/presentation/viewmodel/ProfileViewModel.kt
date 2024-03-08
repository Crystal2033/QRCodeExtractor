package com.crystal2033.qrextractor.auth_feature.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.auth_feature.data.dto.UserLoginDTO
import com.crystal2033.qrextractor.auth_feature.domain.use_case.LoginUserUseCase
import com.crystal2033.qrextractor.auth_feature.presentation.vm_view_communication.UIUserLoginEvent
import com.crystal2033.qrextractor.auth_feature.presentation.vm_view_communication.UserLoginEvent
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel @AssistedInject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    @ApplicationContext private val context: Context,
    @Assisted private val onLoginUser: (User) -> Unit
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(onLoginUser: (User) -> Unit): ProfileViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            onLoginUser: (User) -> Unit
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(onLoginUser) as T
            }
        }
    }


    private val _userLoginDTO = mutableStateOf(UserLoginDTO())
    val userLoginDTO: State<UserLoginDTO> = _userLoginDTO

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: State<User?> = _user

    private val _eventFlow = Channel<UIUserLoginEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    fun onEvent(event: UserLoginEvent) {
        when (event) {
            is UserLoginEvent.OnLoginChanged -> {
                _userLoginDTO.value =
                    UserLoginDTO(
                        login = event.login,
                        password = _userLoginDTO.value.password
                    )
            }

            is UserLoginEvent.OnPasswordChanged -> {
                _userLoginDTO.value =
                    UserLoginDTO(
                        login = _userLoginDTO.value.login,
                        password = event.password
                    )
            }

            UserLoginEvent.OnLoginPressed -> {
                viewModelScope.launch {
                    loginUserUseCase(_userLoginDTO.value)
                        .onEach { status ->
                            makeActionsByStatus(status)
                        }.launchIn(this)
                }
            }
        }
    }

    fun isLoginAndPasswordFilled(): Boolean {
        return _userLoginDTO.value.login.isNotBlank() && _userLoginDTO.value.password.isNotBlank()
    }

    private fun makeActionsByStatus(data: Resource<User?>) {
        when (data) {
            is Resource.Loading -> {
            }

            is Resource.Error -> {
                sendUiEvent(
                    UIUserLoginEvent.OnAuthError(
                        errorMessage = data.message ?: "Unknown error"
                    )
                )
            }

            is Resource.Success -> {
                _user.value = data.data
                onLoginUser(_user.value!!)
                sendUiEvent(UIUserLoginEvent.OnSuccessLoginNavigate(context.resources.getString(R.string.profile_route)))
            }
        }
    }


    private fun sendUiEvent(event: UIUserLoginEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}