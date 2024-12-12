package com.dicoding.cektandur.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dicoding.cektandur.data.pref.UserPreferences
import com.dicoding.cektandur.databinding.FragmentProfileBinding
import com.dicoding.cektandur.ui.history.HistoryActivity
import com.dicoding.cektandur.ui.login.LoginActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userPreferences = UserPreferences.getInstance(requireContext())
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            val userName = userPreferences.userName.first()
            binding.tvUsername.text = userName
        }

        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                userPreferences.clearSession()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
        }

        binding.btnHistoryScan.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}