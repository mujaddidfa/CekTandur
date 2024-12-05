package com.dicoding.cektandur.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.cektandur.data.repository.AuthRepository
import com.dicoding.cektandur.di.Injection
import com.dicoding.cektandur.ui.register.RegisterViewModel

class RegisterViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(repository) as T
    }

    companion object {
        @Volatile
        private var INSTANCE: RegisterViewModelFactory? = null

        fun getInstance(context: Context): RegisterViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RegisterViewModelFactory(Injection.provideAuthRepository(context))
            }.also { INSTANCE = it }
    }
}