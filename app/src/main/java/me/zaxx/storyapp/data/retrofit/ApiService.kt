package me.zaxx.storyapp.data.retrofit

import me.zaxx.storyapp.data.retrofit.response.DetailResponse
import me.zaxx.storyapp.data.retrofit.response.LoginResponse
import me.zaxx.storyapp.data.retrofit.response.RegisterResponse
import me.zaxx.storyapp.data.retrofit.response.StoryResponse
import me.zaxx.storyapp.data.retrofit.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @Multipart
    @POST("stories")
    suspend fun uploadFile(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part ("description") description: RequestBody
    ): UploadResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization")token :String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): StoryResponse

    @GET("stories/{id}")
    fun getDetailStories(
        @Header("Authorization")token: String,
        @Path("id")id :String
    ): Call<DetailResponse>

    @GET("stories")
    fun getStoriesWithLocation(
        @Header("Authorization")token: String,
        @Query("location") location : Int = 1
    ): Call<StoryResponse>
}