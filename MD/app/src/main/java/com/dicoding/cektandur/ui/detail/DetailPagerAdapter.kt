package com.dicoding.cektandur.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class DetailPagerAdapter(fm: FragmentManager, plantDescription: String?) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        Detail1Fragment.newInstance(plantDescription),
        Detail2Fragment()
    )

    override fun getCount(): Int = pages.size

    override fun getItem(position: Int): Fragment = pages[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Tentang"
            1 -> "Jenis Penyakit"
            else -> null
        }
    }
}