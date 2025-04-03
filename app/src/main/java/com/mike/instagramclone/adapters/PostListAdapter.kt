package com.mike.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.mike.instagramclone.Models.Post
import com.mike.instagramclone.databinding.FragmentPostBinding
import com.mike.instagramclone.databinding.ItemPostBinding
import com.squareup.picasso.Picasso

class PostListAdapter(var context: Context): ListAdapter<Post, PostListAdapter.PostViewHolder>(PostListAdapter) {

   // private lateinit var clickedPost: (Post) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {

        return PostViewHolder(ItemPostBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        val post = getItem(position)
        holder.bind(post)
    }


    fun setOnPostClickListener(onClick: (Post) -> Unit) {
       // this.clickedPost = onClick
    }


    class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView3 = binding.imageView3

        fun bind(post: Post) {
            Picasso.get().load(post.image).into(binding.imageView3)
        }
    }

    companion object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return false
        }

    }
}