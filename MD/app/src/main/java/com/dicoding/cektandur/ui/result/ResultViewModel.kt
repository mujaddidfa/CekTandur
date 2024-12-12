package com.dicoding.cektandur.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cektandur.data.api.request.HistoryRequest
import com.dicoding.cektandur.data.api.response.PlantItemResponse
import com.dicoding.cektandur.data.repository.HistoryRepository
import com.dicoding.cektandur.data.repository.PlantRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ResultViewModel(
    private val plantRepository: PlantRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _plantItem = MutableLiveData<PlantItemResponse>()
    val plantItem: LiveData<PlantItemResponse> get() = _plantItem

    fun getPlantById(id: Int) {
        viewModelScope.launch {
            val response = plantRepository.getPlantById(id)
            _plantItem.value = response
        }
    }

    fun addHistory(
        userId: RequestBody,
        className: RequestBody,
        diseaseName: RequestBody,
        confidence: RequestBody,
        plantImage: MultipartBody.Part
    ) {
        viewModelScope.launch {
            historyRepository.addHistory(userId, className, diseaseName, confidence, plantImage)
        }
    }
}