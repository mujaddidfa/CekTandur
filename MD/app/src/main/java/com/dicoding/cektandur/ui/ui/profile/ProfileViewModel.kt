package com.dicoding.cektandur.ui.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    // Menggunakan LiveData untuk menyimpan nama pengguna
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    // Menggunakan LiveData untuk menyimpan URL atau URI foto profil
    private val _profilePhotoUri = MutableLiveData<String>()
    val profilePhotoUri: LiveData<String> get() = _profilePhotoUri

    // Function untuk update nama pengguna
    fun setUsername(name: String) {
        _username.value = name
    }

    // Function untuk update foto profil
    fun setProfilePhotoUri(uri: String) {
        _profilePhotoUri.value = uri
    }

    // Function untuk handling logout
    fun logout() {
        // Implementasi logika logout (misalnya, menghapus session, mengarahkan ke halaman login, dll.)
    }

}