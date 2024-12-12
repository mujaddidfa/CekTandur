package com.dicoding.cektandur.ui.detail

import android.os.Bundle
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
        val plantId = arguments?.getInt("PLANT_ID") ?: return
        viewModel.getAllPlants().observe(viewLifecycleOwner) { plants ->
            val filteredDiseases =
                plants?.filter { it?.idPlant in getPlantIdRange(plantId) }?.filterNotNull()
                    ?: emptyList()
            binding.recyclerView.adapter = DiseaseAdapter(filteredDiseases)
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