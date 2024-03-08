package com.crystal2033.qrextractor.auth_feature.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.crystal2033.qrextractor.core.LOG_TAG_NAMES
import com.crystal2033.qrextractor.core.model.User

class UserHolderViewModel : ViewModel() {
    //we need to it to share out user in
    // all screens and don`t lose user
    private val _user = mutableStateOf<User?>(null)
    val userState: State<User?> = _user

    fun setUser(user: User) {
        _user.value = user
    }
}