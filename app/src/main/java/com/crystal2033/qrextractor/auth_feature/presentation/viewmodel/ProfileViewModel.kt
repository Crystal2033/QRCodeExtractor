package com.crystal2033.qrextractor.auth_feature.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crystal2033.qrextractor.R
import com.crystal2033.qrextractor.auth_feature.data.dto.UserLoginDTO
import com.crystal2033.qrextractor.auth_feature.domain.use_case.LoginUserUseCase
import com.crystal2033.qrextractor.auth_feature.presentation.state.UserLoginDTOState
import com.crystal2033.qrextractor.auth_feature.presentation.vm_view_communication.UIUserLoginEvent
import com.crystal2033.qrextractor.auth_feature.presentation.vm_view_communication.UserLoginEvent
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.User
import com.crystal2033.qrextractor.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel() {

//    private val _login = mutableStateOf("")
//    val login: State<String> = _login
//
//    private val _password = mutableStateOf("")
//    val password: State<String> = _password

    private val _userLoginDTOState = mutableStateOf(UserLoginDTOState())
    val userLoginDTOState: State<UserLoginDTOState> = _userLoginDTOState

//    private val _userLoginDTO = mutableStateOf(UserLoginDTO())
//    val userLoginDTO : State<UserLoginDTO> = _userLoginDTO

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: State<User?> = _user

    private val _eventFlow = Channel<UIUserLoginEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    fun onEvent(event: UserLoginEvent) {
        when (event) {
            is UserLoginEvent.OnLoginChanged -> {
                _userLoginDTOState.value.loginState.value = event.login
            }

            is UserLoginEvent.OnPasswordChanged -> {
                _userLoginDTOState.value.passwordState.value = event.password
            }

            UserLoginEvent.OnLoginPressed -> {
                viewModelScope.launch {
                    loginUserUseCase(
                        UserLoginDTO(
                            _userLoginDTOState.value.loginState.value,
                            _userLoginDTOState.value.passwordState.value
                        )
                    )
                        .onEach { status ->
                            makeActionsByStatus(status)
                        }.launchIn(this)
                }
            }
        }
    }

    private fun makeActionsByStatus(data: Resource<User?>) {
        when (data) {
            is Resource.Loading -> {
                Log.i(LOG_TAG_NAMES.INFO_TAG, "Loading")
            }

            is Resource.Error -> {
                Log.e(LOG_TAG_NAMES.ERROR_TAG, "Error")
            }

            is Resource.Success -> {
                _user.value = data.data
                sendUiEvent(UIUserLoginEvent.Navigate(context.resources.getString(R.string.profile_route)))
            }
        }
    }


    private fun sendUiEvent(event: UIUserLoginEvent) {
        viewModelScope.launch {
            _eventFlow.send(event)
        }
    }
}