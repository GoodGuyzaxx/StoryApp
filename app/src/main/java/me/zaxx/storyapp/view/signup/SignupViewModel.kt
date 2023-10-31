package me.zaxx.storyapp.view.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import me.zaxx.storyapp.data.repository.StoryRepository
import me.zaxx.storyapp.data.retrofit.response.RegisterResponse
import retrofit2.HttpException

class SignupViewModel(private val repository: StoryRepository): ViewModel() {
    private val _signupResponse = MutableLiveData<RegisterResponse>()
    val signupResponse: LiveData<RegisterResponse> = _signupResponse

    fun getRegister(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(name, email, password)
                _signupResponse.postValue(response)
            }catch (e: HttpException) {
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, RegisterResponse::class.java)
                val errorMessage = errorBody.message
                _signupResponse.postValue(errorBody)
                Log.d(TAG, "getLogin: $errorMessage")
            }
        }
    }

    companion object {
        private val TAG = SignupViewModel::class.java.simpleName
    }
}