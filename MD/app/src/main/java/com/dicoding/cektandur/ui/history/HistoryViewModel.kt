package com.dicoding.cektandur.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cektandur.data.api.response.DataItem
import com.dicoding.cektandur.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {

    private val _historyList = MutableLiveData<List<DataItem>>()
    val historyList: LiveData<List<DataItem>> get() = _historyList

    fun getHistory(userId: String) {
        viewModelScope.launch {
            val response = historyRepository.getHistory(userId)
            _historyList.value = response.data?.filterNotNull() ?: emptyList()
        }
    }
}