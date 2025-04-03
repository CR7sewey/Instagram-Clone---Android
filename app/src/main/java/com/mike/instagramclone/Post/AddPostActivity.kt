package com.mike.instagramclone.Post

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mike.instagramclone.R
import com.mike.instagramclone.databinding.ActivityAddPostBinding

class AddPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding
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

        binding.materialToolbar.setNavigationOnClickListener {
            Log.d("AQUI","AQUI")
            finish()
        }

    }
}