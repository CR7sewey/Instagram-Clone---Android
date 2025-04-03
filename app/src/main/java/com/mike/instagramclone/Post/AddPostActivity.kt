package com.mike.instagramclone.Post

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
import com.mike.instagramclone.R
import com.mike.instagramclone.databinding.ActivityAddPostBinding
import com.mike.instagramclone.utils.POST
import com.mike.instagramclone.utils.POST_FOLDER
import com.mike.instagramclone.utils.Utils

class AddPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var post: Post

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            uri ->
        uri?.let { it ->
            Utils().uploadImage(it, POST_FOLDER) {
                    imageUrl ->
                if (imageUrl != null) {
                    binding.imageView1.setImageURI(it)
                    post.image = imageUrl
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
        binding = ActivityAddPostBinding.inflate(layoutInflater)
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

        post = Post()

        binding.materialToolbar.setNavigationOnClickListener {
            Log.d("AQUI","AQUI")
            finish()
        }

        binding.imageView1.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.cancel.setOnClickListener {
            startActivity(Intent(this@AddPostActivity, HomeActivity::class.java))
            finish()
        }

        binding.post.setOnClickListener {
            if (binding.imageView1.getDrawable() == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            }
            else if (binding.caption.editText?.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter a caption", Toast.LENGTH_SHORT).show()
            }
            else {
                post.caption = binding.caption.editText?.text.toString()
                post.uid = Firebase.auth.currentUser?.uid!!
                Toast.makeText(this, "Post created", Toast.LENGTH_SHORT).show()
                savePost()
                finish()
            }

        }

    }

    private fun savePost() {
        db = FirebaseFirestore.getInstance()
        db.collection(POST).document().set(post)
            .addOnSuccessListener { documentReference ->
                db.collection(Firebase.auth.currentUser?.uid!!).document().set(post)
                    .addOnSuccessListener {
                    Toast.makeText(
                        this@AddPostActivity,
                        "Post saved: ${post.image.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
            }

            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this@AddPostActivity,
                    "Register failed: ${e.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()

            }
    }
}