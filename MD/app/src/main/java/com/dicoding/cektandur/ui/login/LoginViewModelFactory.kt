package com.dicoding.cektandur.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.cektandur.data.repository.AuthRepository
import com.dicoding.cektandur.di.Injection

class LoginViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginViewModelFactory? = null

        fun getInstance(): LoginViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LoginViewModelFactory(Injection.provideAuthRepository())
            }.also { INSTANCE = it }
    }
}