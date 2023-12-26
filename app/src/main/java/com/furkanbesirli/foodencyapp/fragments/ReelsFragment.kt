    package com.furkanbesirli.foodencyapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2.Orientation
import com.furkanbesirli.foodencyapp.Models.Reels
import com.furkanbesirli.foodencyapp.R
import com.furkanbesirli.foodencyapp.adapters.ReelsAdapter
import com.furkanbesirli.foodencyapp.databinding.FragmentReelsBinding
import com.furkanbesirli.foodencyapp.utils.REEL
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


    class ReelsFragment : Fragment() {

    private lateinit var binding: FragmentReelsBinding

    lateinit var adapter :ReelsAdapter
    var reelList = ArrayList<Reels>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentReelsBinding.inflate(inflater, container, false)

        adapter = ReelsAdapter(requireContext(),reelList)
        binding.viewPager.adapter=adapter

        Firebase.firestore.collection(REEL).get().addOnSuccessListener {

            var tempList = ArrayList<Reels>()
            reelList.clear()

            for(i in it.documents){
                var reel=i.toObject<Reels>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()

        }

        return binding.root
    }

    companion object {

    }
}