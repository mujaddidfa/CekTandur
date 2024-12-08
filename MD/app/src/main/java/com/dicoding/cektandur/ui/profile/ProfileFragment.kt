package com.dicoding.cektandur.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.cektandur.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameTextView: TextView = binding.usernameTextView
        val profileImageView: ImageView = binding.profileImageView
        val logoutButton: Button = binding.logoutButton

        // Observe dan update UI
        profileViewModel.username.observe(viewLifecycleOwner) {
            usernameTextView.text = it
        }


        profileViewModel.profilePhotoUri.observe(viewLifecycleOwner) { uri ->
            Glide.with(this).load(uri).into(profileImageView)
        }

        logoutButton.setOnClickListener {
            profileViewModel.logout()
            // Add navigation to login page or close app etc. if needed
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
