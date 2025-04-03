package com.mike.instagramclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mike.instagramclone.Models.Reel
import com.mike.instagramclone.R
import com.mike.instagramclone.adapters.ReelAdapter
import com.mike.instagramclone.adapters.ReelListAdapter
import com.mike.instagramclone.databinding.FragmentReelBinding
import com.mike.instagramclone.test.PostList
import com.mike.instagramclone.utils.REEL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReelFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentReelBinding
    private lateinit var adapter: ReelAdapter
    private lateinit var reelList: ArrayList<Reel>

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
        binding = FragmentReelBinding.inflate(inflater, container, false)

        adapter = ReelAdapter(requireContext(), R.drawable.user)
        binding.viewpager.adapter = adapter
        binding.viewpager.orientation = ViewPager2.ORIENTATION_VERTICAL


        var fakeList = PostList.reelSlides // getData()
        fakeList = fakeList.map { it ->
            it.image = R.drawable.image.toString()
            return@map it
        }

        /*Firebase.firestore.collection(REEL).get().addOnSuccessListener { it ->
            if (it == null) return@addOnSuccessListener
            var list = arrayListOf<Reel>()
            for (i in it.documents) {
                var reel = i.toObject(Reel::class.java) as Reel
                list.add(reel)

            }
            reelList.addAll(list)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }*/

        Log.d("PICS",fakeList.toString())


        //GlobalScope.launch(Dispatchers.Main) {
        adapter.submitList(fakeList)


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReelFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReelFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}