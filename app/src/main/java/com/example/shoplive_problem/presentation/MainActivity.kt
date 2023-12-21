package com.example.shoplive_problem.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoplive_problem.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val tabList = listOf("SEARCH", "FAVORITE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            viewpager.adapter = TabAdapter(this@MainActivity.supportFragmentManager, lifecycle)
            TabLayoutMediator(tlTab, viewpager) { tab, position ->
                tab.text = tabList[position]
            }.attach()
        }
    }
}