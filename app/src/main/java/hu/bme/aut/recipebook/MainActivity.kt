package hu.bme.aut.recipebook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import hu.bme.aut.recipebook.extension.validateNonEmpty
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_main)

        btnRegister.setOnClickListener { registerClick() }
        btnLogin.setOnClickListener { loginClick() }

        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, RecipesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateForm() = etEmail.validateNonEmpty() && etPassword.validateNonEmpty()

    private fun registerClick() {
        if (!validateForm()) {
            return
        }


        firebaseAuth
            .createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
            .addOnSuccessListener { result ->

                val firebaseUser = result.user
                val profileChangeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(firebaseUser?.email?.substringBefore('@'))
                    .build()
                firebaseUser?.updateProfile(profileChangeRequest)

                toast(getString(R.string.registration))

                firebaseAuth
                    .signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                    .addOnSuccessListener {

                        startActivity(Intent(this@MainActivity, RecipesActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener { exception ->

                        toast(exception.localizedMessage)
                    }
            }
            .addOnFailureListener { exception ->

                toast(exception.message)
            }
    }

    private fun loginClick() {
        if (!validateForm()) {
            return
        }


        firebaseAuth
            .signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
            .addOnSuccessListener {

                startActivity(Intent(this@MainActivity, RecipesActivity::class.java))
                finish()
            }
            .addOnFailureListener { exception ->

                toast(exception.localizedMessage)
            }
    }

}
