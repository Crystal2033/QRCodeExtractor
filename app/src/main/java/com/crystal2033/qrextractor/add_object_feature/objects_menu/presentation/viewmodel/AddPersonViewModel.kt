package com.crystal2033.qrextractor.add_object_feature.objects_menu.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal2033.qrextractor.core.model.User
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext


class AddPersonViewModel @AssistedInject constructor(
    @Assisted private val user: User,
    @ApplicationContext private val context: Context

) : BaseAddObjectViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(user: User?): AddPersonViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            user: User?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(user) as T
            }
        }
    }


}