package me.zaxx.storyapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.zaxx.storyapp.data.di.Injection
import me.zaxx.storyapp.data.repository.StoryRepository
import me.zaxx.storyapp.view.detail.DetailViewModel
import me.zaxx.storyapp.view.login.LoginViewModel
import me.zaxx.storyapp.view.main.MainViewModel
import me.zaxx.storyapp.view.maps.MapsViewModel
import me.zaxx.storyapp.view.signup.SignupViewModel
import me.zaxx.storyapp.view.upload.UploadViewModel

class ViewModelFactory(private val repository: StoryRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(SignupViewModel::class.java)){
            return SignupViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(UploadViewModel::class.java)){
            return UploadViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(MapsViewModel::class.java)){
            return MapsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class: ${modelClass.name}")
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory?  =null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it}
    }
}