package com.mike.instagramclone.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mike.instagramclone.Models.User
import com.mike.instagramclone.SignUpActivity
import com.mike.instagramclone.adapters.ViewPagerAdapter
import com.mike.instagramclone.databinding.FragmentProfileBinding
import com.mike.instagramclone.utils.USER
import com.mike.instagramclone.utils.USER_PROFILE_FOLDER
import com.mike.instagramclone.utils.Utils
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProfileBinding
    private lateinit var user: User
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val page2Callback = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            Log.d("TAG", position.toString())

            binding.tablayout.setOnClickListener {
                binding.tablayout.getTabAt(position)
                binding.viewpager.currentItem = position
            }

        }

    }

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            uri ->
        uri?.let { it ->
            Utils().uploadImage(it, USER_PROFILE_FOLDER) {
                    imageUrl ->
                if (imageUrl != null) {
                    user.image = imageUrl
                    //binding.profileImage.setImageURI(it)
                   // binding.plus.setImageURI(it)
                }
                else {
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



    }

   /* private fun updateProfile() {
        // [START update_profile]
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            photoUri = Uri.parse(binding.profileImage.toString())
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "User profile updated.")
                }
            }
        // [END update_profile]
    }*/

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER).document(Firebase.auth.currentUser?.uid ?: "").get().addOnSuccessListener { it ->
            var user = it.toObject(User::class.java)!!
            binding.username.text = user.username
            binding.name.text = user.username
            binding.bio.text = user.username + "    " + user.email

            if (!user.image.isNullOrEmpty()) {
                var image = user.image as Uri
                Picasso.get().load(image).into(binding.profileImage)
            }

            binding.plus.setOnClickListener {
                launcher.launch("image/*")
            }

            binding.edit.setOnClickListener {
                val intent = Intent(activity, SignUpActivity::class.java)
                intent.apply {
                    putExtra("MODE", 1)
                }
                activity?.startActivity(intent)
                activity?.finish()
            }


        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)

        viewPagerAdapter.addFragment(MyPostFragment(), "My Post")
        viewPagerAdapter.addFragment(MyReelsFragment(), "My Reels")

        binding.viewpager.adapter = viewPagerAdapter

        binding.viewpager.registerOnPageChangeCallback(page2Callback)


        binding.tablayout.addTab(binding.tablayout.newTab().setText(viewPagerAdapter.getPageTitle(0)))
        binding.tablayout.addTab(binding.tablayout.newTab().setText(viewPagerAdapter.getPageTitle(1)))

        TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, position ->
            tab.text = viewPagerAdapter.getPageTitle(position)}.attach()

        return binding.root
    }
    // https://www.youtube.com/watch?app=desktop&v=ufZhpgoHLv8
    // https://www.youtube.com/watch?v=Y946tVSRQmc

    // Not needed
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(ARG_PARAM1, 0)
        val viewPager = binding.viewpager
        Log.d("POS,", position.toString())
        position?.let {
            when(position)
                {
                0 -> binding.tablayout.getTabAt(0)
                1 -> binding.tablayout.getTabAt(1)

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewpager.unregisterOnPageChangeCallback(page2Callback)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}