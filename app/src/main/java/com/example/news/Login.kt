package com.example.news

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.news.databinding.LoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity()
{
    private lateinit var binding: LoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        //initializing googleSignInClient
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(getString(R.string.default_web_client_id))
                                    .requestEmail()
                                    .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    fun signUpPage(v: View?)
    {
        startActivity(Intent(this, SignUp::class.java))
    }

    fun login(v: View?)
    {
        val email = binding.emailid.text.toString()
        val pass = binding.password.text.toString()

        if(email.isNotEmpty() && pass.isNotEmpty())
        {
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                if(it.isSuccessful)
                {
                    Toast.makeText(this, "Logged in successfully", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, HomePage::class.java))
                }
                else
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
        else
            Toast.makeText(this, "Every field is required", Toast.LENGTH_LONG).show()

//        startActivity(Intent(this, HomePage::class.java))
    }


    fun signInWithGoogle(v: View?)
    {
        val signInIntent = googleSignInClient.signInIntent

        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result ->
        if(result.resultCode == Activity.RESULT_OK)
        {
            //task will select an account from this intent
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            if(task.isSuccessful)
            {
                val account: GoogleSignInAccount? = task.result

                //
                if(account != null)
                {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                    //signing in with credential
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful)
                        {
                            Toast.makeText(this, "logged in with " + account.email, Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, HomePage::class.java))
                        }
                        else
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
                else
                    Toast.makeText(this, "You have to select an account", Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
        }
        else
            Toast.makeText(this, result.resultCode, Toast.LENGTH_LONG).show()
    }

}