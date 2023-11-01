package me.zaxx.storyapp.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import me.zaxx.storyapp.databinding.ActivitySignupBinding
import me.zaxx.storyapp.view.ViewModelFactory
import me.zaxx.storyapp.view.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel by viewModels<SignupViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()


        signupViewModel.signupResponse.observe(this){
            val name = binding.nameEditText.text.toString()
            if (it.error){
                AlertDialog.Builder(this).apply {
                    setTitle("Peringatan")
                    setMessage(it.message)
                    setPositiveButton("OK") {_,_ ->

                    }
                    create()
                    show()
                }
            }else{
                AlertDialog.Builder(this).apply {
                    setTitle("Yeahh!!")
                    setMessage("Akun anda Sudah Dibuat ${name}")
                    setPositiveButton("Lanjut") {_,_ ->
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            }

        }

        binding.btnsignup.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditTextEditText.text.toString()
            signupViewModel.getRegister(name, email, password)
        }
    }


    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val description1 = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1F).setDuration(100)
        val nameTv = ObjectAnimator.ofFloat(binding.nameEditLayout, View.ALPHA, 1F).setDuration(100)
        val nameEdit = ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 1F).setDuration(100)
        val emailTv = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1F).setDuration(100)
        val emailEdit = ObjectAnimator.ofFloat(binding.emailEditLayout, View.ALPHA, 1F).setDuration(100)
        val passwordTv = ObjectAnimator.ofFloat(binding.passwordEditTextEditText, View.ALPHA, 1F).setDuration(100)
        val passwordEdit = ObjectAnimator.ofFloat(binding.passwordEditLayout, View.ALPHA, 1F).setDuration(100)
        val signupButton = ObjectAnimator.ofFloat(binding.btnsignup, View.ALPHA, 1F).setDuration(100)

        AnimatorSet().apply {
            playSequentially(description1,nameTv,nameEdit,emailTv,emailEdit,passwordTv,passwordEdit,signupButton)
            start()
        }
    }

}