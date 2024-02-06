package com.furkanbesirli.foodencyapp

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.furkanbesirli.foodencyapp.Models.User
import com.furkanbesirli.foodencyapp.databinding.ActivitySignUpBinding
import com.furkanbesirli.foodencyapp.utils.USER_NODE
import com.furkanbesirli.foodencyapp.utils.USER_PROFILE_FOLDER
import com.furkanbesirli.foodencyapp.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class SignUpActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    lateinit var user : User

    val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){

            uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER){

                if(it==null){

                }else{
                    user.image=it
                    binding.profileImage.setImageURI(uri)
                }

            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val text = "<font color=#FF000000>Zaten bir hesabın var mı?</font> <font color=#1E88E5> Giriş Yap</font>"
        binding.login.setText(Html.fromHtml(text))
        user = User()

        if(intent.hasExtra("MODE")){

            if(intent.getIntExtra("MODE", -1)==1){

                binding.signUpBtn.text="Profili Güncelle"

                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {

                    user = it.toObject<User>()!!
                    if(!user.image.isNullOrEmpty()){
                        Picasso.get().load(user.image).into(binding.profileImage)

                    }
                    binding.name.editText?.setText(user.name)
                    binding.email.editText?.setText(user.email)
                    binding.password.editText?.setText(user.password)

                }

            }
        }

        binding.signUpBtn.setOnClickListener {

            if (intent.hasExtra("MODE")) {

                if (intent.getIntExtra("MODE", -1) == 1) {

                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@SignUpActivity,
                                "Güncelleme Başarılı!",
                                Toast.LENGTH_LONG
                            ).show()
                            startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                            finish()
                        }
                }
            } else {
            if (binding.name.editText?.text.toString().equals("") or
                binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
            ) {
                Toast.makeText(
                    this@SignUpActivity,
                    "Lütfen bütün bilgileri giriniz!",
                    Toast.LENGTH_LONG
                ).show()

            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        user.name = binding.name.editText?.text.toString()
                        user.email = binding.email.editText?.text.toString()
                        user.password = binding.password.editText?.text.toString()
                        Firebase.firestore.collection(USER_NODE)
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this@SignUpActivity,
                                    "Kayıt Başarılı!",
                                    Toast.LENGTH_LONG
                                ).show()
                                startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                                finish()
                            }

                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            result.exception?.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


            }

         }
        }

        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.login.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }

    }
}
