package com.mike.instagramclone.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mike.instagramclone.Models.Post
import com.mike.instagramclone.Models.User
import com.mike.instagramclone.databinding.ItemProfilesBinding
import com.mike.instagramclone.utils.USER

class ProfilesListAdapter(var context: Context): ListAdapter<User, ProfilesListAdapter.ProfilesListAdapterViewHolder>(ProfilesListAdapter) {

    // private lateinit var clickedPost: (Post) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfilesListAdapterViewHolder {

        return ProfilesListAdapterViewHolder(
            ItemProfilesBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(
        holder: ProfilesListAdapterViewHolder,
        position: Int
    ) {
        val user = getItem(position)
        holder.bind(user)
    }


    fun setOnPostClickListener(onClick: (Post) -> Unit) {
        // this.clickedPost = onClick
    }


    class ProfilesListAdapterViewHolder(private val binding: ItemProfilesBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(user: User) {

            Glide.with(context).load(user.image).placeholder(com.mike.instagramclone.R.drawable.user).into(binding.profileImage)
            binding.name.text = user.username

            var currentUser: User = User()

            Firebase.firestore.collection(USER).get().addOnSuccessListener { it ->
                if (it == null) return@addOnSuccessListener
                for (i in it.documents) {
                    Log.d("TAG", i.id)
                    Log.d("TAG", Firebase.auth.currentUser!!.uid)
                    if (i.id == Firebase.auth.currentUser!!.uid) {
                        currentUser = i.toObject(User::class.java)!!
                        Log.d("TAG", currentUser.toString())
                        break
                    }
                }
            }



            Log.d("TAG 2", currentUser.toString())
            if (currentUser.following == null) {
                currentUser.following = mutableListOf<User>() as ArrayList<User>?
            }

            if (currentUser.followers == null) {
                currentUser.followers = mutableListOf<User>() as ArrayList<User>?
            }

            for (i in currentUser.following!!) {
                Log.d("TAG 3", i.username.toString())
                if (i.username == user.username) {
                    binding.follow.backgroundTintList = context.resources.getColorStateList(com.mike.instagramclone.R.color.blue)
                    binding.follow.text = "following"
                    binding.follow.setTextColor(context.resources.getColor(com.mike.instagramclone.R.color.white))

                }
            }



            binding.follow.setOnClickListener {
                if (currentUser.following == null) {
                    currentUser.following = mutableListOf<User>() as ArrayList<User>?
                }

                if (currentUser.followers == null) {
                    currentUser.followers = mutableListOf<User>() as ArrayList<User>?
                }

                if (!currentUser.following!!.contains(user)) {
                    binding.follow.backgroundTintList =
                        context.resources.getColorStateList(com.mike.instagramclone.R.color.blue)
                    binding.follow.text = "following"
                    binding.follow.setTextColor(context.resources.getColor(com.mike.instagramclone.R.color.white))
                    currentUser.following!!.add(user)
                    Firebase.firestore.collection(USER).document(Firebase.auth.currentUser!!.uid).set(currentUser)

                }
                else {
                    binding.follow.backgroundTintList =
                        context.resources.getColorStateList(com.mike.instagramclone.R.color.white)
                    binding.follow.text = "follow"
                    binding.follow.setTextColor(context.resources.getColor(com.mike.instagramclone.R.color.blue))
                    currentUser.following!!.remove(user)
                    Firebase.firestore.collection(USER).document(Firebase.auth.currentUser!!.uid).set(currentUser)

                }
            }


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