package me.zaxx.storyapp.view.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.zaxx.storyapp.R
import me.zaxx.storyapp.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}