package com.dicoding.cektandur.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.cektandur.data.repository.AuthRepository
import com.dicoding.cektandur.di.Injection

class RegisterViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(repository) as T
    }

    companion object {
        @Volatile
        private var INSTANCE: RegisterViewModelFactory? = null

        fun getInstance(): RegisterViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RegisterViewModelFactory(Injection.provideAuthRepository())
            }.also { INSTANCE = it }
    }
}