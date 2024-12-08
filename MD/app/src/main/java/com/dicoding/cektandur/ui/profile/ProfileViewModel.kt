package com.dicoding.cektandur.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    fun setUsername(name: String) {
        _username.value = name
    }

    fun logout() {
        _username.value = ""
    }

}