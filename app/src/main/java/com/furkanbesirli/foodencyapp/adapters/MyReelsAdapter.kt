package com.furkanbesirli.foodencyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.furkanbesirli.foodencyapp.Models.Reels
import com.furkanbesirli.foodencyapp.databinding.MyReelsRvDesignBinding

class MyReelsAdapter (var context: Context, var reelList: ArrayList<Reels>) :
    RecyclerView.Adapter<MyReelsAdapter.ViewHolder>() {


    inner class ViewHolder(var binding: MyReelsRvDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding = MyReelsRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(reelList.get(position).reelUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.reelsImagee)

    }



}