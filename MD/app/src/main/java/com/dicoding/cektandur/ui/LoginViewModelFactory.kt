package com.dicoding.cektandur.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.cektandur.data.repository.AuthRepository
import com.dicoding.cektandur.di.Injection
import com.dicoding.cektandur.ui.login.LoginViewModel

class LoginViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginViewModelFactory? = null

        fun getInstance(context: Context): LoginViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LoginViewModelFactory(Injection.provideAuthRepository(context))
            }.also { INSTANCE = it }
    }
}