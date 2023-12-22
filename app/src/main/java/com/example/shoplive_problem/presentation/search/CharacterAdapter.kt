package com.example.shoplive_problem.presentation.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.shoplive_problem.R
import com.example.shoplive_problem.databinding.ItemCharacterBinding
import com.example.shoplive_problem.domain.model.Character
import com.example.shoplive_problem.presentation.extensions.click
import com.example.shoplive_problem.presentation.extensions.getDpToPx

class CharacterAdapter(private val onClickCharacter: (Character) -> Unit) :
    ListAdapter<Character, CharacterViewHolder>(object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding, onClickCharacter)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.data = currentList[position]
        holder.bind()
    }
}

class CharacterViewHolder(
    private val binding: ItemCharacterBinding,
    private val onClick: (Character) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    var data: Character? = null

    init {
        binding.root.click {
            data?.let {
                onClick.invoke(it)
            }
        }
    }

    fun bind() {
        data?.let { _data ->
            with(binding) {
                Log.i("!!!", "${_data.thumbnailUrl}")
                Glide.with(root.context)
                    .load(_data.thumbnailUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error_image)
                    .transform(
                        MultiTransformation(
                            CenterCrop(),
                            RoundedCorners(root.context.getDpToPx(8f))
                        )
                    )
                    .into(ivThumbnail)
                tvName.text = _data.name
                tvDescription.text = _data.description
            }
        }

    }

}