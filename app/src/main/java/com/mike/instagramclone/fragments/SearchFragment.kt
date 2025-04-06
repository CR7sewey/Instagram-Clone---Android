package com.mike.instagramclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mike.instagramclone.Models.User
import com.mike.instagramclone.adapters.ProfilesListAdapter
import com.mike.instagramclone.databinding.FragmentSearchBinding
import com.mike.instagramclone.utils.USER
import kotlinx.coroutines.flow.MutableStateFlow

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: ProfilesListAdapter
    private var text: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        adapter = ProfilesListAdapter(requireContext())
        binding.rvPosts.adapter = adapter




        /*Firebase.firestore.collection(USER).get().addOnSuccessListener { it ->
            if (it == null) return@addOnSuccessListener
            var list = arrayListOf<User>()

            for (i in it.documents) {
                var user = i.toObject(User::class.java)
                if (Firebase.auth.currentUser!!.uid != i.id && user != null
                    && user.username!!.contains(text)
                ) {
                    list.add(user)
                }
            }
            adapter.submitList(list)
        }*/
        adapter.submitList(getData())
        binding.searchView.setOnClickListener { it ->
            Log.d("TAG 11", it.toString())
            text = binding.searchView.text.toString()
            if (it.toString().trim().isEmpty()) {
                adapter.submitList(getData())
                return@setOnClickListener
            }
            text = it.toString()
            adapter.submitList(getData(text)) // getData()
        }



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getData(text: String? = null): ArrayList<User> {
        var listPosts = arrayListOf<User>()
        //.whereEqualTo("username", texto)
        Firebase.firestore.collection(USER).get().addOnSuccessListener { it ->
            if (it == null) return@addOnSuccessListener
            var list = arrayListOf<User>()
            for (i in it.documents) {
                var user = i.toObject(User::class.java)
                if (Firebase.auth.currentUser!!.uid != i.id && user != null) {
                    if (text != null && text.toString().trim().isNotEmpty() == true) {
                        Log.d("TAG 12", text.toString())

                        if (user.username!!.contains(text)) {
                            list.add(user)
                        }
                    } else {
                        Log.d("TAG 13", text.toString())
                        list.add(user)
                    }
                }
            }
            listPosts.addAll(list)
            adapter.notifyDataSetChanged()
        }
        return listPosts
    }
}