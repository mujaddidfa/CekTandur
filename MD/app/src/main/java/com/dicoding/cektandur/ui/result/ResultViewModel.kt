package com.dicoding.cektandur.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cektandur.data.api.response.PlantItemResponse
import com.dicoding.cektandur.data.repository.PlantRepository
import kotlinx.coroutines.launch

class ResultViewModel(private val plantRepository: PlantRepository) : ViewModel() {

    private val _plantItem = MutableLiveData<PlantItemResponse>()
    val plantItem: LiveData<PlantItemResponse> get() = _plantItem

    fun getPlantById(id: Int) {
        viewModelScope.launch {
            val response = plantRepository.getPlantById(id)
            _plantItem.value = response
        }
    }
}