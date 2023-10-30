package me.zaxx.storyapp.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)
