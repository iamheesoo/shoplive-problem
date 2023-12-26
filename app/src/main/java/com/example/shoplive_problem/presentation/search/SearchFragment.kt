package com.example.shoplive_problem.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
        onClickCharacter = {
            // 프로그레스바가 돌아갈 때는 아이템 클릭을 막아 다른 동작을 못하게끔 한다
            if (viewModel.isLoading.value != true) {
                if (it.isFavorite) {
                    viewModel.deleteFavorite(it)
                } else {
                    viewModel.addFavorite(it)
                }
            }
        },
        isFavoriteDim = true
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

        viewModel.isLoading.observe(this) {
            binding.clProgress.isVisible = it
        }

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

                    // 스크롤이 최하단에 갔는지 && 마지막 아이템이 보여졌는지
                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                    if (!recyclerView.canScrollVertically(1)
                        && lastVisibleItemPosition == recyclerView.adapter?.itemCount?.minus(1)
                    ) {
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