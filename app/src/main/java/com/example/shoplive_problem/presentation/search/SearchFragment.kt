package com.example.shoplive_problem.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.shoplive_problem.databinding.FragmentSearchBinding
import com.example.shoplive_problem.presentation.extensions.showToast
import kotlinx.coroutines.flow.launchIn
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.widget.editorActionEvents

class SearchFragment : Fragment() {
    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }
    private val viewModel: SearchViewModel by viewModel()
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
            Log.i("!!!", "SUCCESS $it")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            etSearch.editorActionEvents {
                if (it.actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val text = etSearch.text.trim().toString()
                    if (text.isNotEmpty()) {
                        viewModel.getSearchResult(text)
                    }
                }
                false
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }
}