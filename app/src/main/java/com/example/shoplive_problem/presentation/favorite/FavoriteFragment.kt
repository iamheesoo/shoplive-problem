package com.example.shoplive_problem.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoplive_problem.databinding.FragmentFavoriteBinding
import com.example.shoplive_problem.presentation.extensions.showToast
import com.example.shoplive_problem.presentation.common.CharacterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private val binding: FragmentFavoriteBinding by lazy {
        FragmentFavoriteBinding.inflate(layoutInflater)
    }
    private val viewModel: FavoriteViewModel by viewModel()


    private val adapter = CharacterAdapter(
        onClickCharacter = {
            // 프로그레스바가 돌아갈 때는 아이템 클릭을 막아 다른 동작을 못하게끔 한다
            if (viewModel.isLoading.value != true) {
                viewModel.deleteFavorite(it)
            }
        },
        isFavoriteDim = false
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
            with(binding) {
                adapter.submitList(it)
                if (it.isNotEmpty()) {
                    rvList.isVisible = true
                    layoutNoItem.root.isVisible = false
                } else {
                    rvList.isVisible = false
                    layoutNoItem.root.isVisible = true
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvList.layoutManager = GridLayoutManager(root.context, 2)
            rvList.adapter = adapter
            rvList.itemAnimator = null
            rvList.setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()
        // ViewPager로 생성된 fragment는 탭 전환시 최초 생성 후 onResume부터 호출된다
        viewModel.updateCharacterList()
    }
}