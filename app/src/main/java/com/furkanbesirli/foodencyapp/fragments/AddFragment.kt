package com.furkanbesirli.foodencyapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furkanbesirli.foodencyapp.databinding.FragmentAddBinding
import com.furkanbesirli.foodencyapp.reels.ReelsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentAddBinding.inflate(inflater,container,false)


        binding.reelsAdd.setOnClickListener {

        activity?.startActivity(Intent(requireActivity(),ReelsActivity::class.java))

        }

        return binding.root

    }

    companion object {


    }
}