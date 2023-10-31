package me.zaxx.storyapp.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import me.zaxx.storyapp.data.pref.UserModel
import me.zaxx.storyapp.data.repository.StoryRepository
import me.zaxx.storyapp.data.retrofit.response.LoginResponse
import retrofit2.HttpException


class LoginViewModel(private val repository: StoryRepository): ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getLogin(email: String, password: String){
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
//                val successResponse = ApiConfig.getApiService("").login(email, password)
                saveSession(UserModel(
                    response.loginResult.userId,
                    response.loginResult.name,
                    response.loginResult.token,
                    true
                ))
                _loginResponse.postValue(response)
            } catch (e: HttpException) {
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, LoginResponse::class.java)
                val errorMessage = errorBody.message
                _isLoading.postValue(false)
                _loginResponse.postValue(errorBody)
                Log.d(TAG, "getLogin: $errorMessage")
            }
        }
    }

    private fun saveSession(user: UserModel){
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    companion object{
        private val TAG = LoginViewModel::class.java.simpleName
    }
}

