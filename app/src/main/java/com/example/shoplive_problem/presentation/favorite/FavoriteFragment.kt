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
import com.example.shoplive_problem.presentation.search.CharacterAdapter
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
            adapter.submitList(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvList.layoutManager = GridLayoutManager(root.context, 2)
            rvList.adapter = adapter
            rvList.itemAnimator = null
        }
    }

    override fun onResume() {
        super.onResume()
        // ViewPager로 생성된 fragment는 탭 전환시 viewCreated를 호출하지 않는다
       viewModel.updateCharacterList()
    }
}