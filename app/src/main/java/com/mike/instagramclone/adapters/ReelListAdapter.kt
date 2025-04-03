package com.mike.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mike.instagramclone.Models.Post
import com.mike.instagramclone.Models.Reel
import com.mike.instagramclone.databinding.ItemPostBinding
import com.mike.instagramclone.databinding.ItemReelBinding
import com.squareup.picasso.Picasso

class ReelListAdapter(var context: Context): ListAdapter<Reel, ReelListAdapter.ReelViewHolder>(ReelListAdapter) {

    // private lateinit var clickedPost: (Post) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReelViewHolder {

        return ReelViewHolder(ItemReelBinding.inflate(LayoutInflater.from(context), parent, false), context)
    }

    override fun onBindViewHolder(
        holder: ReelViewHolder,
        position: Int
    ) {
        val post = getItem(position)
        holder.bind(post)
    }


    fun setOnPostClickListener(onClick: (Post) -> Unit) {
        // this.clickedPost = onClick
    }


    class ReelViewHolder(private val binding: ItemReelBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reel: Reel) {
            Glide.with(context).load(reel.image).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.videoView)

        }
    }

    companion object : DiffUtil.ItemCallback<Reel>() {
        override fun areItemsTheSame(oldItem: Reel, newItem: Reel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Reel, newItem: Reel): Boolean {
            return false
        }

    }
}