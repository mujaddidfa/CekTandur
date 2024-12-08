package com.dicoding.cektandur.ui.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    // Menggunakan LiveData untuk menyimpan nama pengguna
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username
    
    // Function untuk update nama pengguna
    fun setUsername(name: String) {
        _username.value = name
    }


    // Function untuk handling logout
    fun logout() {
        // Clear user data/session
        _username.value = ""

        // Note: You may want to add more clearing operations here
        // like clearing SharedPreferences or other user data
        
        // The actual navigation to login screen should be handled in the UI/Activity layer
        // not in ViewModel to maintain separation of concerns
    }

}