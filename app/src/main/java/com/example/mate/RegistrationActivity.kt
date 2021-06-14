package com.example.mate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordConfirmationEditText: EditText
    private lateinit var submitButton: Button

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()     


        setContentView(R.layout.activity_registration)

        this.init()
        this.registerListener()
    }

    private fun init() {

        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        passwordConfirmationEditText = findViewById(R.id.editTextPasswordConfirm)
        submitButton = findViewById(R.id.submit)
    }

    private fun registerListener() {
        submitButton.setOnClickListener {
            val email: String = emailEditText.text.toString()
            val password: String = passwordEditText.text.toString()
            val confpassword: String = passwordConfirmationEditText.text.toString()

            if (email.isEmpty() || password.isEmpty() || confpassword.isEmpty()){
                Toast.makeText(this, "Fill everything!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confpassword) {
                Toast.makeText(this, "Password was unsuccessful!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!EmailValid(email)){
                Toast.makeText(this, "Email - incorrect !", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!PasswordValid(password)) {
                Toast.makeText(this, "Password doesn't meet the requirements!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    goToReg()
                }
                else{
                    Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    fun EmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun PasswordValid(password: String): Boolean{
        val reg = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{9,}".toRegex()
        return reg.matches(password)
    }

    private fun goToReg(){
        startActivity(Intent(this, RegistrationActivity::class.java))
        finish()
    }
}