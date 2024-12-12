package com.dicoding.cektandur.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.cektandur.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantDescription = intent.getStringExtra("PLANT_DESCRIPTION")
        val plantCover = intent.getIntExtra("PLANT_COVER", -1)
        val plantId = intent.getIntExtra("PLANT_ID", -1)

        if (plantCover != -1) {
            binding.ivCover.setImageResource(plantCover)
        }

        val adapter = DetailPagerAdapter(supportFragmentManager, plantId, plantDescription)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}