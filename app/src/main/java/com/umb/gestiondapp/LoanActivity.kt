package com.umb.gestiondapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.umb.gestiondapp.adapters.LoanAdapter
import com.umb.gestiondapp.models.LoanModel
import kotlinx.android.synthetic.main.loan_activity.*

class LoanActivity : AppCompatActivity() {

    private val loanAdapter by lazy { LoanAdapter() }
    private val database = Firebase.database
    private var myRef: DatabaseReference = database.getReference("prestamos")
    private val loans = ArrayList<LoanModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loan_activity)
        rcvLoan.adapter = loanAdapter
        rcvLoan.layoutManager = LinearLayoutManager(this)
        initFirebaseListener()
    }

    private fun initFirebaseListener() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loans.clear()
                dataSnapshot.children.forEach {
                    //Gson().fromJson(it.value, LoanModel)
                    val loadModel = it.getValue<LoanModel>() ?: LoanModel()
                    loans.add(loadModel)
                }
                loanAdapter.setList(loans)
            }

            override fun onCancelled(error: DatabaseError) {

                print(error.toException())
            }
        })
    }
}