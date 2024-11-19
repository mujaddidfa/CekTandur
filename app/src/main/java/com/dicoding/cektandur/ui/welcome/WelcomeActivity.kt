package com.dicoding.cektandur.ui.welcome

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.cektandur.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = WelcomePagerAdapter(this)
        binding.viewPager.adapter = adapter

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val viewPager = binding.viewPager
                if (viewPager.currentItem == 0) {
                    finish()
                } else {
                    viewPager.currentItem -= 1
                }
            }
        })
    }
}