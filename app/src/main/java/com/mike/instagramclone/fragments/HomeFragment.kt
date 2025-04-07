package com.mike.instagramclone.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mike.instagramclone.Models.Post
import com.mike.instagramclone.Models.User
import com.mike.instagramclone.R
import com.mike.instagramclone.SignUpActivity
import com.mike.instagramclone.adapters.PostListAdapter
import com.mike.instagramclone.adapters.PostListHomeAdapter
import com.mike.instagramclone.adapters.ReelListAdapter
import com.mike.instagramclone.adapters.StoriesListAdapter
import com.mike.instagramclone.databinding.FragmentHomeBinding
import com.mike.instagramclone.test.PostList
import com.mike.instagramclone.utils.POST
import com.mike.instagramclone.utils.USER
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var postAdapter: PostListHomeAdapter
    private lateinit var storiesAdapter: StoriesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER).document(Firebase.auth.currentUser?.uid ?: "").get().addOnSuccessListener { it ->
            var user = it.toObject(User::class.java)!!

            if (!user.image.isNullOrEmpty()) {
                var image = user.image as Uri
                Picasso.get().load(image).into(binding.profileImage)
            }




        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)




        var rv = binding.rvPosts
        postAdapter = PostListHomeAdapter(requireContext())
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = postAdapter

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar)

        var fakeList = PostList.postSlides // getData()
        fakeList = fakeList.map { it ->
            it.image = R.drawable.image.toString()
            return@map it
        }

        Log.d("PICS",fakeList.toString())


        //GlobalScope.launch(Dispatchers.Main) {
        postAdapter.submitList(fakeList)
        //}

        var rvStories = binding.rvStories
        storiesAdapter = StoriesListAdapter(requireContext())
        rvStories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvStories.adapter = storiesAdapter
        storiesAdapter.submitList(getDataStories())


        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getData(): ArrayList<Post> {
        var listPosts = arrayListOf<Post>()
        listPosts.clear()
        Firebase.firestore.collection(POST).get().addOnSuccessListener { it ->
            if (it == null) return@addOnSuccessListener
            var list = arrayListOf<Post>()
            for (i in it.documents) {
                var post = i.toObject(Post::class.java)
                if (post != null) {
                    list.add(post)
                }

            }
            listPosts.addAll(list)
            postAdapter.notifyDataSetChanged()
        }
        return listPosts
    }

    private fun getDataStories(): ArrayList<User> {
        var listPosts = arrayListOf<User>()
        //.whereEqualTo("username", texto)



        Firebase.firestore.collection(USER).get().addOnSuccessListener { it ->
            if (it == null) return@addOnSuccessListener
            var list = arrayListOf<User>()
            var following: List<User> = emptyList<User>()
            for (i in it.documents) {
                var user = i.toObject(User::class.java)
                if (Firebase.auth.currentUser!!.uid == i.id) {

                    following = user?.following!!
                    }
                }

            listPosts.addAll(following)
            storiesAdapter.notifyDataSetChanged()
        }
        return listPosts
    }
}