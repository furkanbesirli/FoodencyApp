package com.furkanbesirli.foodencyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.furkanbesirli.foodencyapp.Models.User
import com.furkanbesirli.foodencyapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    lateinit var user : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    user = User()

        //burası aslında private val launcher
        val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){

                uri ->
            uri?.let {

            }

        }

        binding.signUpBtn.setOnClickListener {
            if(binding.name.editText?.text.toString().equals("") or
                binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
            ){
                Toast.makeText(this@SignUpActivity, "Lütfen bütün bilgileri giriniz!", Toast.LENGTH_LONG).show()

            }else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener {
                    result ->
                    if(result.isSuccessful){
                        user.name=binding.name.editText?.text.toString()
                        user.email=binding.email.editText?.text.toString()
                        user.password=binding.password.editText?.text.toString()
                        Firebase.firestore.collection("User")
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this@SignUpActivity, "Kayıt Başarılı!", Toast.LENGTH_LONG).show()
                            }


                    }else{
                        Toast.makeText(this@SignUpActivity, result.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                }


            }


        }
    }
}
