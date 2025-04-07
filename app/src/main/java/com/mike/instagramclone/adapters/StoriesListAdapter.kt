package com.mike.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mike.instagramclone.Models.User
import com.mike.instagramclone.databinding.ItemStoriesBinding


class StoriesListAdapter(var context: Context): ListAdapter<User, StoriesListAdapter.StoriesListAdapterViewHolder>(StoriesListAdapter) {
    // private lateinit var clickedPost: (Post) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoriesListAdapterViewHolder {

        return StoriesListAdapterViewHolder(
            ItemStoriesBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(
        holder: StoriesListAdapterViewHolder,
        position: Int
    ) {
        val user = getItem(position)
        holder.bind(user)
    }



    class StoriesListAdapterViewHolder(private val binding: ItemStoriesBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(user: User) {
            Glide.with(context).load(user.image).placeholder(com.mike.instagramclone.R.drawable.user).into(binding.profileImage)

        }

        }




    companion object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return false
        }

    }

}