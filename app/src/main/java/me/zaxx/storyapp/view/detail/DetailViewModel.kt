package me.zaxx.storyapp.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import me.zaxx.storyapp.data.pref.UserModel
import me.zaxx.storyapp.data.repository.StoryRepository

class DetailViewModel(private val repository: StoryRepository): ViewModel() {

    val detailItem = repository.detailItem

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getDetailStory(token: String, id: String) = repository.getDetailStory(token, id)
}