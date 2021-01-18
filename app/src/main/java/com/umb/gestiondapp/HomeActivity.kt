package com.umb.gestiondapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home_activity.*
import java.io.File

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        cnlAssistant.setOnClickListener {
            startActivity(
                Intent(this, LocationsActivity::class.java)
            )
        }

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(
                Intent(this, LoginActivity::class.java).apply {
                    addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    )
                }
            )
        }

        cnlPrestamos.setOnClickListener {
            startActivity(
                Intent(this, LoanOptions::class.java)
            )
        }
    }
}