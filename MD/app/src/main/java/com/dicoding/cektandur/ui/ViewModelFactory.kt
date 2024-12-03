package com.dicoding.cektandur.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.cektandur.data.repository.RegisterRepository
import com.dicoding.cektandur.ui.register.RegisterViewModel

class RegisterViewModelFactory(private val repository: RegisterRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(repository) as T
    }
}