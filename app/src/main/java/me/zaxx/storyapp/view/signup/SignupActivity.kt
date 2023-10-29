package me.zaxx.storyapp.view.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import me.zaxx.storyapp.databinding.ActivitySignupBinding
import me.zaxx.storyapp.view.ViewModelFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel by viewModels<SignupViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


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

}