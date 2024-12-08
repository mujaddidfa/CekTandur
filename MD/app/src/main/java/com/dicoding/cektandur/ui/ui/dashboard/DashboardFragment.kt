package com.dicoding.cektandur.ui.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.cektandur.R
import com.dicoding.cektandur.databinding.FragmentDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide BottomNavigationView
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        // Hide BottomNavigationView when returning to this fragment
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav?.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        // Ensure BottomNavigationView is visible when leaving this fragment
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav?.visibility = View.VISIBLE
    }
}


