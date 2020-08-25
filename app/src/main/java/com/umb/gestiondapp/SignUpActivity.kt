package com.umb.gestiondapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity: AppCompatActivity () {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        initListeners()
    }

    private fun initListeners() {
        btnSignup.setOnClickListener {
            when {
                sgnupEmail.text.isNullOrEmpty() -> {
                    sgnupEmail.error = "Campo vacio"
                }
                sgnupPassword.text.isNullOrEmpty() -> {
                    sgnupPassword.error = "Contraseña inválida"
                }
                sgnupPassword.text.length < 6 -> {
                    sgnupPassword.error = "Tu contraseña debe tener mínimo 6 caracteres"
                }
                else -> signUp()
            }
        }
    }


    fun signUp() {
        auth.createUserWithEmailAndPassword(sgnupEmail.text.toString(), sgnupPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                } else {

                }
            }
    }

}