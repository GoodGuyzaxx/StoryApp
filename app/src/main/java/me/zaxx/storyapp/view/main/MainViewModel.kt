package me.zaxx.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import me.zaxx.storyapp.data.pref.UserModel
import me.zaxx.storyapp.data.repository.StoryRepository
import me.zaxx.storyapp.data.retrofit.response.ListStoryItem

class MainViewModel(private val repository: StoryRepository): ViewModel() {

 //    val listItem = repository.listItem
 //    val listItem: LiveData<PagingData<ListStoryItem>> by lazy {
 //        repository.getStories("Bearer ${repository.getToken()}").cachedIn(viewModelScope)
 //    }
    fun getStory(token: String) : LiveData<PagingData<ListStoryItem>> = repository.getStories(token).cachedIn(viewModelScope)


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}