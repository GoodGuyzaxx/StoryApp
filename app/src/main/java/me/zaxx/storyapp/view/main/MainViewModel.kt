package me.zaxx.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.zaxx.storyapp.data.pref.UserModel
import me.zaxx.storyapp.data.repository.StoryRepository
import me.zaxx.storyapp.data.retrofit.ApiConfig
import me.zaxx.storyapp.data.retrofit.response.ListStoryItem
import me.zaxx.storyapp.data.retrofit.response.StoryResponse
import retrofit2.Callback
import java.util.concurrent.Flow

class MainViewModel(private val repository: StoryRepository): ViewModel() {


    val listItem = repository.listItem
    fun getStory(token: String) = repository.getStories(token)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}