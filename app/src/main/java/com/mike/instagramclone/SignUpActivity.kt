package com.mike.instagramclone

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.mike.instagramclone.Models.User
import com.mike.instagramclone.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        var username = binding.username
        var email = binding.emailContainer
        var password = binding.password
        var register = binding.register
        var login = binding.login


        register.setOnClickListener {
            if (username.editText?.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please insert your username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email.editText?.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please insert your email", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }
            if (password.editText?.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please insert your password", Toast.LENGTH_SHORT).show()

                return@setOnClickListener}

            var user: User = User(username.editText?.text.toString(), email.editText?.text.toString(), password.editText?.text.toString())

            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        login.setOnLongClickListener {



            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            true
        }


    }


}