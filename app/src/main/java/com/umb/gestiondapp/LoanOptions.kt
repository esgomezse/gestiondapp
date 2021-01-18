package com.umb.gestiondapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umb.gestiondapp.LoanActivity.Companion.BORROWED
import com.umb.gestiondapp.LoanActivity.Companion.KIND_OF_LOAN
import com.umb.gestiondapp.LoanActivity.Companion.NEW
import kotlinx.android.synthetic.main.estados.*

/**
 * Created By Juan Felipe Arango on 17/01/21
 * Copyright ©2020 Merqueo. All rights reserved
 */
class LoanOptions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.estados)
        cnlLoansAvailables.setOnClickListener {
            startActivity(
                Intent(this, LoanActivity::class.java).apply {
                    putExtra(KIND_OF_LOAN, NEW)
                }
            )
        }
        cnlLoansInProgress.setOnClickListener {
            startActivity(
                Intent(this, LoanActivity::class.java).apply {
                    putExtra(KIND_OF_LOAN, BORROWED)
                }
            )
        }
        cnlLoansReturned.setOnClickListener {
            startActivity(
                Intent(this, LoanActivity::class.java)
            )
        }
    }
}