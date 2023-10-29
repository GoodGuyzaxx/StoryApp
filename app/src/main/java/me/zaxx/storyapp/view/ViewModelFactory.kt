package me.zaxx.storyapp.view

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.zaxx.storyapp.data.di.Injection
import me.zaxx.storyapp.data.repository.StoryRepository
import me.zaxx.storyapp.view.login.LoginViewModel
import me.zaxx.storyapp.view.signup.SignupViewModel

class ViewModelFactory(private val repository: StoryRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(SignupViewModel::class.java)){
            return SignupViewModel(repository) as T
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

//    companion object{
//        @Volatile
//        private var INSTANCE: ViewModelFactory? = null
//
//        @JvmStatic
//        fun getInstance(repository: StoryRepository): ViewModelFactory {
//            if (INSTANCE == null){
//                synchronized(ViewModelFactory::class.java) {
//                    INSTANCE = ViewModelFactory(repository)
//                }
//            }
//            return INSTANCE as ViewModelFactory
//        }
//    }
}