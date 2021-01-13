package com.umb.gestiondapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.umb.gestiondapp.adapters.InventoryAdapter
import com.umb.gestiondapp.adapters.LoanAdapter
import com.umb.gestiondapp.models.LoanModel
import kotlinx.android.synthetic.main.activity_inventory_filter.*

class LoanActivity : AppCompatActivity() {

    private val loanAdapter by lazy { LoanAdapter ()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loan_activity)
        rcvInventory.adapter = loanAdapter
        rcvInventory.layoutManager = LinearLayoutManager(this)

        val list = listOf(
            LoanModel(
                "Estefania Gomez",
                "12345",
                "Silla",
                "Ingenieria Hospitalaria",
                "A1"

            ),
            LoanModel(
                "Estefania Portela",
                "67891",
                "Muletas",
                "Ingenieria Clinica",
                "A2"

            )
        )
        loanAdapter.setList(list)
    }
}