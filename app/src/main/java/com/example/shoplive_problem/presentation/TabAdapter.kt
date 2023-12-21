package com.example.shoplive_problem.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shoplive_problem.presentation.favorite.FavoriteFragment
import com.example.shoplive_problem.presentation.search.SearchFragment

class TabAdapter(
    private val fragmentManager: FragmentManager,
    private val lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchFragment()
            1 -> FavoriteFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}