package com.furkanbesirli.foodencyapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.furkanbesirli.foodencyapp.Models.Reels
import com.furkanbesirli.foodencyapp.R
import com.furkanbesirli.foodencyapp.adapters.MyReelsAdapter
import com.furkanbesirli.foodencyapp.databinding.FragmentMyReelsBinding
import com.furkanbesirli.foodencyapp.utils.REEL
import com.furkanbesirli.foodencyapp.utils.REEL_FOLDER
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class MyReelsFragment : Fragment() {

    private lateinit var binding: FragmentMyReelsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentMyReelsBinding.inflate(inflater, container, false)

        var reelList=ArrayList<Reels>()
        var adapter= MyReelsAdapter(requireContext(), reelList)
        binding.rv.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter=adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).get().addOnSuccessListener {
            var tempList= arrayListOf<Reels>()
            for(i in it.documents){
                var reel: Reels= i.toObject<Reels>()!!
                tempList.add(reel)

            }
            reelList.addAll(tempList)
            adapter.notifyDataSetChanged()

        }

        return binding.root

    }

    companion object {


    }
}