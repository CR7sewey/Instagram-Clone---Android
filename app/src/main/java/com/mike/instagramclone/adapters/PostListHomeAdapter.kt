package com.mike.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mike.instagramclone.Models.Post
import com.mike.instagramclone.databinding.ItemPostBinding
import com.mike.instagramclone.databinding.PostsLayoutBinding
import com.squareup.picasso.Picasso

class PostListHomeAdapter(var context: Context): ListAdapter<Post, PostListHomeAdapter.PostHomeViewHolder>(PostListHomeAdapter) {

    // private lateinit var clickedPost: (Post) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostHomeViewHolder {

        return PostHomeViewHolder(
            PostsLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: PostHomeViewHolder,
        position: Int
    ) {
        val post = getItem(position)
        holder.bind(post)
    }


    fun setOnPostClickListener(onClick: (Post) -> Unit) {
        // this.clickedPost = onClick
    }


    class PostHomeViewHolder(private val binding: PostsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageView3 = binding.imageView4

        fun bind(post: Post) {
            Picasso.get().load(post.image).into(binding.imageView4)
            binding.caption.text = post.caption
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
