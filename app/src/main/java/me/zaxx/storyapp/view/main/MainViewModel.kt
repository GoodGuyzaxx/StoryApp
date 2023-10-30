package me.zaxx.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.zaxx.storyapp.data.pref.UserModel
import me.zaxx.storyapp.data.repository.StoryRepository
import java.util.concurrent.Flow

class MainViewModel(private val repository: StoryRepository): ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}