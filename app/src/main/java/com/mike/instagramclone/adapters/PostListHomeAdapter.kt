package com.mike.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mike.instagramclone.Models.Post
import com.mike.instagramclone.Models.User
import com.mike.instagramclone.databinding.ItemPostBinding
import com.mike.instagramclone.databinding.PostsLayoutBinding
import com.mike.instagramclone.utils.USER
import com.mike.instagramclone.utils.USER_PROFILE_FOLDER
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
            ), context
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


    class PostHomeViewHolder(private val binding: PostsLayoutBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(post: Post) {
            try {


            Firebase.firestore.collection(USER_PROFILE_FOLDER).document(post.uid!!).get().addOnSuccessListener {
                if (it != null) {
                    var user = it.toObject(User::class.java)
                    Glide.with(context).load(user?.image).into(binding.profileImage)
                }

            }
            }
            catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
                return
            }
            Glide.with(context).load(post.image).into(binding.imageView4)
            binding.caption.text = post.caption
            binding.time.text = post.time
            binding.name.text = post.uname
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
