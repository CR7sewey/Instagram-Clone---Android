package com.mike.instagramclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.mike.instagramclone.Models.User
import com.mike.instagramclone.SignUpActivity
import com.mike.instagramclone.databinding.ActivitySignInBinding
import com.mike.instagramclone.utils.USER

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var user: User
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        var email = binding.emailContainer
        var password = binding.password
        var login = binding.login
        var register = binding.register

        register.setOnLongClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            true
        }

        login.setOnClickListener {
            if (email.editText?.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please insert your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.editText?.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please insert your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            user = User(null, password.editText?.text.toString(), email.editText?.text.toString(), null)
            signIn()

        }

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }

    }

    private fun reload() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }


    private fun signIn() {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(user.email!!, user.password!!)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "signInWithEmail:success")
                    val userAuth = auth.currentUser

                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Failure", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        // [END sign_in_with_email]

    }



}