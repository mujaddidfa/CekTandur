package com.dicoding.cektandur.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.cektandur.data.repository.LoginRepository
import com.dicoding.cektandur.ui.login.LoginViewModel

class LoginViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}