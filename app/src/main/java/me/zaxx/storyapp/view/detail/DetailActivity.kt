package me.zaxx.storyapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import me.zaxx.storyapp.data.retrofit.response.Story
import me.zaxx.storyapp.databinding.ActivityDetailBinding
import me.zaxx.storyapp.view.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val getId = intent.getStringExtra(GET_ID).toString()

        detailViewModel.detailItem.observe(this){
            getDetainItem(it)
        }

        detailViewModel.getSession().observe(this){
            detailViewModel.getDetailStory(it.token,getId)
        }

    }

    private fun getDetainItem(getStory: Story) {
        binding.apply {
            Glide
                .with(root.context)
                .load(getStory.photoUrl)
                .into(ivDetail)
            tvDetailName.text = getStory.name
            tvDetailDescription.text = getStory.description
        }
    }

    companion object {
        const val GET_ID = "USERID"
    }
}