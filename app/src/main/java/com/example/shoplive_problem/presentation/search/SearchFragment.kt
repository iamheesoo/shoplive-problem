package com.example.shoplive_problem.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplive_problem.databinding.FragmentSearchBinding
import com.example.shoplive_problem.presentation.extensions.showToast
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.widget.textChanges

class SearchFragment : Fragment() {

    companion object {
        const val SEARCH_WORD_MIN_LENGTH = 2
    }

    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }
    private val viewModel: SearchViewModel by viewModel()
    private val adapter = CharacterAdapter(
        onClickCharacter = {}
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.toastMessage.observe(this) {
            binding.root.context.showToast(it)
        }

        viewModel.characterList.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvList.layoutManager = GridLayoutManager(root.context, 2)
            rvList.adapter = adapter

            rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        viewModel.getSearchResult()
                    }
                }
            })

            etSearch
                .textChanges()
                .skipInitialValue()
                .map { it.trim() }
                .debounce(300L)
                .onEach { text ->
                    viewModel.searchText = text.toString()
                    if (text.length > SEARCH_WORD_MIN_LENGTH) {
                        viewModel.getSearchResult()
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }
}