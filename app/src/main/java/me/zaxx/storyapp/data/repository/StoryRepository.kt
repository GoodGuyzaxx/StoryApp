package me.zaxx.storyapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import me.zaxx.storyapp.data.pref.UserModel
import me.zaxx.storyapp.data.pref.UserPreference
import me.zaxx.storyapp.data.retrofit.ApiConfig
import me.zaxx.storyapp.data.retrofit.ApiService
import me.zaxx.storyapp.data.retrofit.response.DetailResponse
import me.zaxx.storyapp.data.retrofit.response.ListStoryItem
import me.zaxx.storyapp.data.retrofit.response.RegisterResponse
import me.zaxx.storyapp.data.retrofit.response.Story
import me.zaxx.storyapp.data.retrofit.response.StoryResponse
import me.zaxx.storyapp.data.retrofit.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository private constructor(private val apiService: ApiService,private val userPreference: UserPreference){

    private val _listItem = MutableLiveData<List<ListStoryItem>>()
    val listItem: LiveData<List<ListStoryItem>> =_listItem

    private val _detailItem = MutableLiveData<Story>()
    val detailItem: LiveData<Story> = _detailItem

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

    fun getDetailStory(token: String, id:String){
        val client = apiService.getDetailStories("Bearer $token", id)
        client.enqueue(object : Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful){
                    _detailItem.value = response.body()?.story!!
                }else Log.e("TAG", "onResponse: ${response.message()}", )
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message.toString()}")
            }

        })
    }

    suspend fun addStory(
        token: String,
        multipartBody: MultipartBody.Part,
        description: RequestBody):UploadResponse {
        return apiService.uploadFile("Bearer $token", multipartBody, description)
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

