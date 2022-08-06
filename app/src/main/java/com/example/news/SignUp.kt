package com.example.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.example.news.databinding.SignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity()
{
    private lateinit var binding: SignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
    }

    fun loginPage(v: View?)
    {
        startActivity(Intent(this, Login::class.java))
    }

    fun signUp(v: View?)
    {
        val email = binding.emailid.text.toString()
        val pass = binding.password.text.toString()
        val checkbox = binding.checkBox as CheckBox

        if(email.isNotEmpty() && pass.isNotEmpty())
        {
            //checking if the terms and conditions is accepted
            if(! checkbox.isChecked)
            {
                Toast.makeText(this, "You need to accept the term and condition", Toast.LENGTH_LONG).show()
                return
            }


            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                if(it.isSuccessful)
                {
                    Toast.makeText(this, "Signed up successfully", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, Login::class.java))
                }
                else
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
        else
            Toast.makeText(this, "Every field is required", Toast.LENGTH_LONG).show()
    }

}