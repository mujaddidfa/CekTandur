package com.dicoding.cektandur.ui.scan

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanViewModel : ViewModel() {
    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> get() = _currentImageUri

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun setCurrentImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}