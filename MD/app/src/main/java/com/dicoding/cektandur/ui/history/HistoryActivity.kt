package com.dicoding.cektandur.ui.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.cektandur.data.pref.UserPreferences
import com.dicoding.cektandur.databinding.ActivityHistoryBinding
import com.dicoding.cektandur.di.Injection
import com.dicoding.cektandur.ui.HistoryAdapter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    private val viewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory(Injection.provideHistoryRepository())
    }
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences.getInstance(this)

        binding.rvHistory.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val userId = userPreferences.userId.first() ?: return@launch
            viewModel.getHistory(userId)
        }

        viewModel.historyList.observe(this) { historyList ->
            binding.rvHistory.adapter = HistoryAdapter(historyList)
        }
    }
}