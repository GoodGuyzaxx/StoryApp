package me.zaxx.storyapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.zaxx.storyapp.data.pref.UserModel
import me.zaxx.storyapp.data.pref.UserPreference
import me.zaxx.storyapp.data.retrofit.ApiConfig
import me.zaxx.storyapp.data.retrofit.ApiService
import me.zaxx.storyapp.data.retrofit.response.ListStoryItem
import me.zaxx.storyapp.data.retrofit.response.RegisterResponse
import me.zaxx.storyapp.data.retrofit.response.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.security.auth.login.LoginException

class StoryRepository private constructor(private val apiService: ApiService,private val userPreference: UserPreference){

    private val _listItem = MutableLiveData<List<ListStoryItem>>()
    val listItem: LiveData<List<ListStoryItem>> =_listItem

    //For Api
    suspend fun login(email: String,password: String ) = apiService.login(email,password)


    suspend fun register(name: String, email: String,password: String): RegisterResponse{
        return apiService.register(name, email, password)
    }

    fun getStories(token: String){
        val client = ApiConfig.getApiService().getStories("Bearer $token")
        client.enqueue(object : Callback<StoryResponse>{
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if (response.isSuccessful){
                    _listItem.value = response.body()?.listStory
                }else{
                    Log.e("TAG", "onResponse: ${response.message()} ")
                }
            }
            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message.toString()}", )
            }

        })
    }

    //For DataStore
    suspend fun saveSession(user: UserModel){
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
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

