package com.mike.instagramclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mike.instagramclone.Models.Post
import com.mike.instagramclone.R
import com.mike.instagramclone.adapters.PostListAdapter
import com.mike.instagramclone.databinding.FragmentPostBinding
import com.mike.instagramclone.test.PostList
import com.mike.instagramclone.utils.POST
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPostBinding
    private lateinit var adapter: PostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false)

        var rv = binding.rvPosts
        adapter = PostListAdapter(requireContext())
        rv.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        rv.adapter = adapter

        var fakeList = PostList.postSlides // getData()
        fakeList = fakeList.map { it ->
            it.image = R.drawable.image.toString()
            return@map it
        }

        Log.d("PICS",fakeList.toString())


        //GlobalScope.launch(Dispatchers.Main) {
            adapter.submitList(fakeList)
        //}

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getData(): ArrayList<Post> {
        var listPosts = arrayListOf<Post>()
        Firebase.firestore.collection(POST).get().addOnSuccessListener { it ->
            if (it == null) return@addOnSuccessListener
            var list = arrayListOf<Post>()
            for (i in it.documents) {
                var post = i.toObject(Post::class.java)
                if (Firebase.auth.currentUser!!.uid == post!!.uid) {
                    list.add(post)
                }
            }
            listPosts.addAll(list)
            adapter.notifyDataSetChanged()
        }
        return listPosts
    }
}