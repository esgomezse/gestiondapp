package com.umb.gestiondapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.umb.gestiondapp.adapters.LoanAdapter
import com.umb.gestiondapp.models.LoanModel
import kotlinx.android.synthetic.main.loan_activity.*

class LoanActivity : AppCompatActivity() {

    private val loanAdapter by lazy { LoanAdapter() }
    private val database = Firebase.database
    private var myRef: DatabaseReference = database.getReference("prestamos")
    private val loans = ArrayList<LoanModel>()
    private var kindOfLoan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loan_activity)
        kindOfLoan = intent.getStringExtra(KIND_OF_LOAN) ?: ""
        rcvLoan.adapter = loanAdapter
        rcvLoan.layoutManager = LinearLayoutManager(this)
        initFirebaseListener()
        addObservers()
    }

    private fun addObservers() {
        loanAdapter.events.observe(this, Observer {
            when(it.status){
                BORROWED -> startActivity(
                    Intent(this, LocationsActivity::class.java).apply {
                        putExtra(ITEM_LOAN, true)
                    }
                )
                else -> {
                    returnProduct(it)
                }
            }
            ObjectLoan.loanModel = it

        })
    }

    private fun returnProduct(loanModel: LoanModel) {
        loanModel.product.usuarioPrestamo = ""
        val loanMap = loanModel?.toMap()?.toMutableMap() ?: mutableMapOf()
        loanMap["Producto"] = loanModel.product.toMap()
        loanMap["ProductoUbicacion"] = loanModel.productLocation
        loanMap["ProductoID"] = loanModel.productId

        val product = loanModel.product.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/prestamos/${loanModel.id}" to loanMap,
            "/${loanModel.productLocation}/${loanModel.productId}" to product
        )
        myRef = database.getReference("/")
        myRef.updateChildren(childUpdates).addOnSuccessListener {
            initFirebaseListener()
        }.addOnFailureListener {
            Toast.makeText(this, "Algo ha salido mal, vuelve a intentarlo", Toast.LENGTH_SHORT)
                .show()

        }

    }

    private fun initFirebaseListener() {
        myRef = database.getReference("prestamos")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loans.clear()
                dataSnapshot.children.forEach {
                    val loanModel = it.getValue<LoanModel>() ?: LoanModel()
                    loanModel.id = it.key ?: ""
                    if (loanModel.status == kindOfLoan) loans.add(loanModel)
                }
                pgBarLoan.isVisible = false
                loanAdapter.setList(loans)
            }

            override fun onCancelled(error: DatabaseError) {
                print(error.toException())
            }
        })
    }

    companion object {
        const val KIND_OF_LOAN = "kind of loan"
        const val NEW = "nuevo"
        const val BORROWED = "prestado"
        const val ITEM_LOAN = "loan"
    }
}