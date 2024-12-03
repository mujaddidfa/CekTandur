package com.dicoding.cektandur.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cektandur.R
import com.dicoding.cektandur.databinding.FragmentHomeBinding
import com.dicoding.cektandur.ui.PlantAdapter
import com.dicoding.cektandur.ui.login.LoginActivity
import com.dicoding.cektandur.utils.Plant
import com.dicoding.cektandur.utils.SessionManager

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    private lateinit var rvPlant: RecyclerView
    private val list = ArrayList<Plant>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sessionManager = SessionManager(requireContext())

        val userName = sessionManager.getUserName()
        binding.tvWelcome.text = getString(R.string.welcome_home, userName)

        binding.btnLogout.setOnClickListener {
            sessionManager.clearSession()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        rvPlant = binding.rvPlants
        rvPlant.setHasFixedSize(true)
        rvPlant.layoutManager = GridLayoutManager(context, 2)

        list.addAll(getListPlants())
        rvPlant.adapter = PlantAdapter(list)

        return root
    }

    private fun getListPlants(): ArrayList<Plant> {
        val dataName = resources.getStringArray(R.array.plant_name)
        val dataImage = resources.obtainTypedArray(R.array.data_photo)
        val listPlant = ArrayList<Plant>()
        for (i in dataName.indices) {
            val plant = Plant(dataName[i], dataImage.getResourceId(i, -1))
            listPlant.add(plant)
        }
        return listPlant
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}