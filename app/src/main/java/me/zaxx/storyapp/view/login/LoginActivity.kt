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
import me.zaxx.storyapp.R
import me.zaxx.storyapp.databinding.ActivityLoginBinding
import me.zaxx.storyapp.view.main.MainActivity
import me.zaxx.storyapp.view.signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signupText()

        binding.btnlogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
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
}