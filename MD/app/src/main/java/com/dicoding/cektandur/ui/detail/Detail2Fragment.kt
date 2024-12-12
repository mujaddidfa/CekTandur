package com.dicoding.cektandur.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.cektandur.data.api.ApiConfig
import com.dicoding.cektandur.data.repository.PlantRepository
import com.dicoding.cektandur.databinding.FragmentDetail2Binding

class Detail2Fragment : Fragment() {

    private var _binding: FragmentDetail2Binding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(PlantRepository(ApiConfig.getApiService()))
    }

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
        val plantId = arguments?.getInt("PLANT_ID")
        if (plantId == null) {
            Log.e("Detail2Fragment", "PLANT_ID argument is missing")
            return
        }
        Log.d("Detail2Fragment", "PLANT_ID: $plantId")
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getAllPlants().observe(viewLifecycleOwner) { plants ->
            binding.progressBar.visibility = View.GONE
            val filteredDiseases = plants?.filter { it?.idPlant in getPlantIdRange(plantId) }?.filterNotNull() ?: emptyList()
            if (filteredDiseases.isEmpty()) {
                Log.d("Detail2Fragment", "No diseases found for plant ID: $plantId")
            } else {
                Log.d("Detail2Fragment", "Diseases found: ${filteredDiseases.size}, plant ID: $plantId")
            }
            binding.recyclerView.adapter = DiseaseAdapter(filteredDiseases)
        }
    }

    private fun getPlantIdRange(plantId: Int): IntRange {
        return when (plantId) {
            1 -> 1..4
            2 -> 5..8
            3 -> 9..12
            4 -> 16..25
            5 -> 13..15
            else -> IntRange.EMPTY
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}