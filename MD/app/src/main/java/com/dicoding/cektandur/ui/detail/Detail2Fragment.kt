package com.dicoding.cektandur.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.cektandur.data.api.ApiConfig
import com.dicoding.cektandur.databinding.FragmentDetail2Binding
import kotlinx.coroutines.launch

class Detail2Fragment : Fragment() {

    private var _binding: FragmentDetail2Binding? = null
    private val binding get() = _binding!!
    private val apiService = ApiConfig.getApiService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetail2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        fetchDiseases()
    }

    private fun fetchDiseases() {
        lifecycleScope.launch {
            try {
                val response = apiService.getAllPlants()
                val plantId = arguments?.getInt("PLANT_ID") ?: return@launch
                val filteredDiseases = response.plants?.filter { it?.idPlant in getPlantIdRange(plantId) }?.filterNotNull() ?: emptyList()
                binding.recyclerView.adapter = DiseaseAdapter(filteredDiseases)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getPlantIdRange(plantId: Int): IntRange {
        return when (plantId) {
            in 1..4 -> 1..4
            in 5..8 -> 5..8
            in 9..12 -> 9..12
            in 13..15 -> 13..15
            in 16..25 -> 16..25
            else -> IntRange.EMPTY
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}