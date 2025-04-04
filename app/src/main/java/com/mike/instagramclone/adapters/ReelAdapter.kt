package com.mike.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mike.instagramclone.Models.Post
import com.mike.instagramclone.Models.Reel
import com.mike.instagramclone.databinding.ItemPostBinding
import com.mike.instagramclone.databinding.ReelDgBinding
import com.squareup.picasso.Picasso

class ReelAdapter(var context: Context, @DrawableRes var drawableRes: Int): ListAdapter<Reel, ReelAdapter.ReelViewHolder>(ReelAdapter) {

    // private lateinit var clickedPost: (Post) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReelViewHolder {

        return ReelViewHolder(ReelDgBinding.inflate(LayoutInflater.from(context), parent, false), drawableRes)
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


    class ReelViewHolder(private val binding: ReelDgBinding, val drawableRes: Int) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reel: Reel) {
            binding.caption.text = reel.caption
            Picasso.get().load(reel.profileLink).placeholder(drawableRes).into(binding.profileImage)
            binding.video.setVideoPath(reel.image)

            binding.video.setOnPreparedListener { it ->
                binding.progressBar.visibility = ViewGroup.GONE
                it.start()
            }
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