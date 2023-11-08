package me.zaxx.storyapp.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import me.zaxx.storyapp.data.pref.UserModel
import me.zaxx.storyapp.data.repository.StoryRepository

class MapsViewModel(private val repository: StoryRepository): ViewModel() {

    val listItem = repository.listItem

    fun getMapsData(token: String) = repository.getLocationData(token)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}