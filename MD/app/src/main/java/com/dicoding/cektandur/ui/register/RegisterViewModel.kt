package com.dicoding.cektandur.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.cektandur.data.repository.RegisterRepository
import com.dicoding.cektandur.utils.Result
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {

    fun register(name: String, email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val response = repository.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}