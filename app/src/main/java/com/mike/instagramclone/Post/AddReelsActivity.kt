package com.mike.instagramclone.Post

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.mike.instagramclone.HomeActivity
import com.mike.instagramclone.Models.Post
import com.mike.instagramclone.Models.Reel
import com.mike.instagramclone.Post.AddPostActivity
import com.mike.instagramclone.R
import com.mike.instagramclone.databinding.ActivityAddPostBinding
import com.mike.instagramclone.databinding.ActivityAddReelsBinding
import com.mike.instagramclone.utils.POST
import com.mike.instagramclone.utils.POST_FOLDER
import com.mike.instagramclone.utils.REEL
import com.mike.instagramclone.utils.REEL_FOLDER
import com.mike.instagramclone.utils.USER
import com.mike.instagramclone.utils.USER_PROFILE_FOLDER
import com.mike.instagramclone.utils.Utils

class AddReelsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddReelsBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var reel: Reel
    private lateinit var videoURL: String

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            uri ->
        uri?.let { it ->
            Utils().uploadVideo(it, REEL_FOLDER, context = this@AddReelsActivity, progressDialog = ProgressDialog(this@AddReelsActivity)) {
                    url ->
                if (url != null) {
                    videoURL = url
                }
                else {
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddReelsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.materialToolbar)
        // add back arrow to toolbar
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
            supportActionBar?.setDisplayShowHomeEnabled(true);
        }
        binding.materialToolbar.setNavigationOnClickListener {
            Log.d("AQUI","AQUI")
            finish()
        }

        reel = Reel()

        binding.videoView1.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.cancel.setOnClickListener {
            startActivity(Intent(this@AddReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.post.setOnClickListener {
            if (binding.videoView1.getDrawable() == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            }
            else if (binding.caption.editText?.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter a caption", Toast.LENGTH_SHORT).show()
            }
            else {
                reel.caption = binding.caption.editText?.text.toString()
                reel.uid = Firebase.auth.currentUser?.uid!!
                reel.image = videoURL
                saveReel()
                Toast.makeText(this, "Post created", Toast.LENGTH_SHORT).show()
                finish()
            }

        }

    }

    private fun saveReel() {
        db = FirebaseFirestore.getInstance()
        // user
        db.collection(USER_PROFILE_FOLDER).document(Firebase.auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it != null) {
                reel.profileLink = it.get("image").toString()
            }

        }

        db.collection(Firebase.auth.currentUser?.uid!!+REEL).document().set(reel)
            .addOnSuccessListener {
                        Toast.makeText(
                            this@AddReelsActivity,
                            "Post saved: ${reel.image.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this@AddReelsActivity,
                    "Register failed: ${e.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()

            }
    }
}