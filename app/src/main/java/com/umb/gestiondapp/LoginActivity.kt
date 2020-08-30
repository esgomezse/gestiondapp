package com.umb.gestiondapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        initListeners()
    }

    private fun initListeners() {
        btnLogin.setOnClickListener {
            when {
                edtEmail.text.isNullOrEmpty() -> {
                    edtEmail.error = "Campo vacio"
                }
                edtPassword.text.isNullOrEmpty() -> {
                    edtPassword.error = "Campo vacío"
                }
                edtPassword.text.length < 6 -> {
                    edtPassword.error = "Tu contraseña debe tener mínimo 6 caracteres"
                }
                else -> doLogin()
            }


        }
        registro.setOnClickListener {
            startActivity(
                Intent(this,SignUpActivity::class.java)
            )
            finish()
        }
    }

    private fun doLogin() {
        auth.signInWithEmailAndPassword(edtEmail.text.toString(), edtPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(
                        Intent(this,HomeActivity::class.java)
                    )
                    finish()
                } else {
                    Toast.makeText(baseContext, "¡Revisa tus datos!",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}