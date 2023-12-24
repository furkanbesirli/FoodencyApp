package com.furkanbesirli.foodencyapp.reels

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.furkanbesirli.foodencyapp.HomeActivity
import com.furkanbesirli.foodencyapp.Models.Reels
import com.furkanbesirli.foodencyapp.databinding.ActivityLoginBinding
import com.furkanbesirli.foodencyapp.databinding.ActivityReelsBinding
import com.furkanbesirli.foodencyapp.utils.REEL
import com.furkanbesirli.foodencyapp.utils.REEL_FOLDER
import com.furkanbesirli.foodencyapp.utils.uploadVideo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReelsActivity : AppCompatActivity() {


    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }

    private lateinit var videoUrl: String


    private lateinit var progressDialog:ProgressDialog

    private val launcher= registerForActivityResult(ActivityResultContracts.GetContent()) {uri -> uri?.let {

        uploadVideo(uri, REEL, progressDialog

        ){ url ->
            if(url!=null){
                videoUrl=url
            }
        }
    }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        progressDialog= ProgressDialog(this)


        binding.materialToolBar.setOnClickListener {
            finish()
        }


        binding.selectReel.setOnClickListener {

            launcher.launch("video/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.reelsButton.setOnClickListener {

            val reel: Reels = Reels(videoUrl!!, binding.caption.editText?.text.toString())

            Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).document().set(reel).addOnSuccessListener {
                    startActivity(Intent(this@ReelsActivity,HomeActivity::class.java))
                    finish()

                }

            }

        }

    }
}