package me.zaxx.storyapp.data.di

import android.content.Context
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.zaxx.storyapp.data.pref.UserPreference
import me.zaxx.storyapp.data.pref.dataStore
import me.zaxx.storyapp.data.repository.StoryRepository
import me.zaxx.storyapp.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(pref,apiService)
    }
}