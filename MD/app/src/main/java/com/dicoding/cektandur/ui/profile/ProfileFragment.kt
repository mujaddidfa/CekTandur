package com.dicoding.cektandur.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userPreferences = UserPreferences.getInstance(requireContext())
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            val isLogin = userPreferences.isLogin.first()
            if (!isLogin) {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                val userName = userPreferences.userName.first()
                binding.usernameTextView.text = userName
            }
        }

        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                userPreferences.clearSession()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        binding.btnHistoryscan.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameTextView: TextView = binding.usernameTextView
        val logoutButton: Button = binding.btnLogout

        profileViewModel.username.observe(viewLifecycleOwner) {
            usernameTextView.text = it
        }

        logoutButton.setOnClickListener {
            profileViewModel.logout()
            lifecycleScope.launch {
                userPreferences.clearSession()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}