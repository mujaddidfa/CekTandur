package com.dicoding.cektandur.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.cektandur.databinding.FragmentDetail1Binding

class Detail1Fragment : Fragment() {
    private var plantDescription: String? = null
    private var _binding: FragmentDetail1Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            plantDescription = it.getString(ARG_PLANT_DESCRIPTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetail1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvPlantDescription.text = plantDescription
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PLANT_DESCRIPTION = "plant_description"

        @JvmStatic
        fun newInstance(plantDescription: String?) =
            Detail1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PLANT_DESCRIPTION, plantDescription)
                }
            }
    }
}