package com.furkanbesirli.foodencyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.furkanbesirli.foodencyapp.Models.User
import com.furkanbesirli.foodencyapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {

        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            if( binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")){

                Toast.makeText(this@LoginActivity, "Lütfen bilgilerinizi eksiksiz giriniz!", Toast.LENGTH_LONG).show()
            }else{
                var user = User(binding.email.editText?.text.toString(), binding.password.editText?.text.toString())
                Firebase.auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                            finish()

                        }else{
                            Toast.makeText(this@LoginActivity,it.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                        }

                }
            }

        }


    }
}