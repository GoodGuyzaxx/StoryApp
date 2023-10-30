package me.zaxx.storyapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.zaxx.storyapp.data.retrofit.response.ListStoryItem
import me.zaxx.storyapp.data.retrofit.response.StoryResponse
import me.zaxx.storyapp.databinding.ItemStoryListBinding

class StoryListAdapter:ListAdapter<ListStoryItem, StoryListAdapter.StoryViewHolder>(DIFF_CALLBACK) {
    inner class StoryViewHolder(private val binding: ItemStoryListBinding): RecyclerView.ViewHolder(binding.root)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StoryViewHolder(binding
        )
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}