package me.zaxx.storyapp.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.zaxx.storyapp.data.retrofit.response.ListStoryItem
import me.zaxx.storyapp.databinding.ItemStoryListBinding
import me.zaxx.storyapp.view.detail.DetailActivity

class StoryListAdapter:PagingDataAdapter<ListStoryItem, StoryListAdapter.StoryViewHolder>(DIFF_CALLBACK) {
    inner class StoryViewHolder(private val binding: ItemStoryListBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(data: ListStoryItem){
            Glide
                .with(binding.root.context)
                .load(data.photoUrl)
                .into(binding.ivItemList)
            binding.tvItemList.text = data.name

            itemView.setOnClickListener {
                val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                intentDetail.putExtra("USERID",data.id)
                itemView.context.startActivity(intentDetail, ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity).toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StoryViewHolder(binding
        )
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null){
            holder.bind(user)
        }
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