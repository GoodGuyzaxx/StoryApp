package me.zaxx.storyapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.zaxx.storyapp.R
import me.zaxx.storyapp.data.retrofit.ApiConfig
import me.zaxx.storyapp.data.retrofit.response.LoginResponse
import me.zaxx.storyapp.databinding.ActivityLoginBinding
import me.zaxx.storyapp.view.ViewModelFactory
import me.zaxx.storyapp.view.main.MainActivity
import me.zaxx.storyapp.view.signup.SignupActivity
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>{
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signupText()

        loginViewModel.loginResponse.observe(this){
            if (it.error) {
                showToast(it.message)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                showToast("Login ${it.message}")
                finish()
            }
        }

        loginViewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.btnlogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            loginViewModel.getLogin(email,password)
        }

    }

    private fun signupText(){
        val text ="Belum punya akun? Daftar sekarang!"
        val spanString = SpannableString(text)

        val cilckabelSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                widget.context.startActivity(intent)
            }

        }
        spanString.setSpan(cilckabelSpan,18,34,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvSignup.text = spanString
        binding.tvSignup.movementMethod = LinkMovementMethod.getInstance()
        Log.d("text as Button", "signupText: $spanString")
    }

    private fun showToast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}