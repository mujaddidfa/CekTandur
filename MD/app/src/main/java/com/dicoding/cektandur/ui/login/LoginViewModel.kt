package com.dicoding.cektandur.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.cektandur.data.repository.LoginRepository
import com.dicoding.cektandur.utils.Result
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val response = repository.login(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}