package com.mike.instagramclone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.mike.instagramclone.Models.User
import com.mike.instagramclone.databinding.ActivitySignUpBinding
import com.mike.instagramclone.utils.USER
import com.mike.instagramclone.utils.USER_PROFILE_FOLDER
import com.mike.instagramclone.utils.Utils

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var user: User

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        uri ->
        uri?.let { it ->
            Utils().uploadImage(it, USER_PROFILE_FOLDER) {
                imageUrl ->
                if (imageUrl != null) {
                    user.image = imageUrl
                    binding.profileImage.setImageURI(it)
                    binding.plus.setImageURI(it)
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
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()


        var username = binding.username
        var email = binding.emailContainer
        var password = binding.password
        var register = binding.register
        var login = binding.login
        var plus = binding.plus

        plus.setOnClickListener {
            launcher.launch("image/*")
        }


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

            user = User(username.editText?.text.toString(), password.editText?.text.toString(), email.editText?.text.toString())
            createAccount()

        }

        login.setOnLongClickListener {



            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            true
        }


    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        /*val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }*/

    }

    private fun reload() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun createAccount() {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(user.email.toString(), user.password.toString())
            .addOnCompleteListener(this) { task ->
                Log.d("Test", user.email.toString())
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "createUserWithEmail:success")
                    //val user = auth.currentUser
                   // Toast.makeText(this@SignUpActivity, "${user?.email.toString()}", Toast.LENGTH_SHORT).show()
                    saveUser()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Failure", "createUserWithEmail:failure",
                        task.exception
                    )
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        // [END create_user_with_email]
    }

    private fun saveUser() {
        db = FirebaseFirestore.getInstance()
        db.collection(USER).document(Firebase.auth.currentUser!!.uid).set(user).addOnSuccessListener { documentReference ->
            Toast.makeText(this@SignUpActivity, "User saved: ${user.email.toString()}", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener { e ->
                Toast.makeText(this@SignUpActivity, "Register failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }


}