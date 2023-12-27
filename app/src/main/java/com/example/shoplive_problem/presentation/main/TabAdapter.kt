package com.example.shoplive_problem.presentation.main

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
        return TabType.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TabType.SEARCH.ordinal-> SearchFragment()
            TabType.FAVORITE.ordinal -> FavoriteFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}