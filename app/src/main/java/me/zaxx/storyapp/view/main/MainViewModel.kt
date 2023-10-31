package me.zaxx.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.zaxx.storyapp.data.pref.UserModel
import me.zaxx.storyapp.data.repository.StoryRepository

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