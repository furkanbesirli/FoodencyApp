package com.furkanbesirli.foodencyapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furkanbesirli.foodencyapp.Models.User
import com.furkanbesirli.foodencyapp.R
import com.furkanbesirli.foodencyapp.SignUpActivity
import com.furkanbesirli.foodencyapp.adapters.ViewPagerAdapter
import com.furkanbesirli.foodencyapp.databinding.FragmentProfileBinding
import com.furkanbesirli.foodencyapp.utils.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater, container, false)

        binding.editProfile.setOnClickListener {

            val intent = Intent(activity,SignUpActivity::class.java)
            intent.putExtra("MODE", 1)
            activity?.startActivity(intent)
            activity?.finish()

        }

        viewPagerAdapter=ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragments(MyReelsFragment(), "My Reels")
        binding.viewPager.adapter=viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    companion object {

    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {

            val user:User= it.toObject<User>()!!
            binding.name.text=user.name
            binding.bio.text=user.email

            if(!user.image.isNullOrEmpty()){
                Picasso.get().load(user.image).into(binding.profileImage)

            }

        }

    }

}