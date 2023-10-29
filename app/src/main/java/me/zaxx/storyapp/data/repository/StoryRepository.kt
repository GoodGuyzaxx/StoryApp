package me.zaxx.storyapp.data.repository

import kotlinx.coroutines.flow.Flow
import me.zaxx.storyapp.data.pref.UserModel
import me.zaxx.storyapp.data.pref.UserPreference
import me.zaxx.storyapp.data.retrofit.ApiConfig
import me.zaxx.storyapp.data.retrofit.ApiService

class StoryRepository private constructor(private val apiService: ApiService,private val userPreference: UserPreference){

    //For Api
    suspend fun login(email: String,password: String ){
        apiService.login(email,password)
    }

    suspend fun signUp(name: String, email: String,password: String){
        apiService.register(name, email, password)
    }

    //For DataStore
    suspend fun saveSession(user: UserModel){
        userPreference.saveSession(user)
    }

    suspend fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() = userPreference.logout()

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this){
                instance ?: StoryRepository(apiService, userPreference)
            }.also { instance = it }
    }
}

