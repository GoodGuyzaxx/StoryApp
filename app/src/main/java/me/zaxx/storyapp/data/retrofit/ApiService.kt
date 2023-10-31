package me.zaxx.storyapp.data.retrofit

import me.zaxx.storyapp.data.retrofit.response.DetailResponse
import me.zaxx.storyapp.data.retrofit.response.ListStoryItem
import me.zaxx.storyapp.data.retrofit.response.LoginResponse
import me.zaxx.storyapp.data.retrofit.response.LoginResult
import me.zaxx.storyapp.data.retrofit.response.RegisterResponse
import me.zaxx.storyapp.data.retrofit.response.StoryResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name:String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    fun getStories(
        @Header("Authorization")token :String
    ): Call<StoryResponse>

    @GET("stories/{id}")
    fun getDetailStories(
        @Header("Authorization")token: String,
        @Path("id")id :String
    ): Call<DetailResponse>
}