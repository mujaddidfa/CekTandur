package com.dicoding.cektandur.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cektandur.R
import com.dicoding.cektandur.data.pref.UserPreferences
import com.dicoding.cektandur.databinding.FragmentHomeBinding
import com.dicoding.cektandur.ui.PlantAdapter
import com.dicoding.cektandur.ui.login.LoginActivity
import com.dicoding.cektandur.utils.Plant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreferences: UserPreferences

    private lateinit var rvPlant: RecyclerView
    private val list = ArrayList<Plant>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userPreferences = UserPreferences.getInstance(requireContext())

        lifecycleScope.launch {
            val isLogin = userPreferences.isLogin.first()
            if (!isLogin) {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                val userName = userPreferences.userName.first()
                binding.tvWelcome.text = getString(R.string.welcome_home, userName)
            }
        }

        rvPlant = binding.rvPlants
        rvPlant.setHasFixedSize(true)
        rvPlant.layoutManager = GridLayoutManager(context, 2)

        list.clear()
        list.addAll(getListPlants())
        rvPlant.adapter = PlantAdapter(list)

        return root
    }

    @SuppressLint("Recycle")
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