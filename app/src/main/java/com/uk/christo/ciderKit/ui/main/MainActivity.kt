package com.uk.christo.ciderKit.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.uk.christo.ciderKit.R
import com.uk.christo.ciderKit.databinding.ActivityMainBinding
import com.uk.christo.ciderKit.ui.fragments.AddSugarFragment
import com.uk.christo.ciderKit.ui.fragments.SGToAlcFragment

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupViewPager()
    }
    
    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_add_sugar)
                1 -> getString(R.string.tab_sg_to_alcohol)
                else -> ""
            }
        }.attach()
    }
    
    private inner class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        
        override fun getItemCount(): Int = 2
        
        override fun createFragment(position: Int) = when (position) {
            0 -> AddSugarFragment()
            1 -> SGToAlcFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}