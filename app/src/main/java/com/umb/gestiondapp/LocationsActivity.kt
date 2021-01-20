package com.umb.gestiondapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umb.gestiondapp.LoanActivity.Companion.ITEM_LOAN
import com.umb.gestiondapp.models.LoanModel
import kotlinx.android.synthetic.main.locations.*


/**
 * Created By Juan Felipe Arango on 11/11/20
 * Copyright ©2020 Merqueo. All rights reserved
 */
class LocationsActivity : AppCompatActivity() {

    private var loan: LoanModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.locations)
        loan = intent.getParcelableExtra(ITEM_LOAN)
        cnlApartamento.setOnClickListener {
            navigateToOptions(APARTMENT)
        }
        cnlExpo.setOnClickListener {
            navigateToOptions(EXPO)
        }
        cnlSupplier.setOnClickListener {
            navigateToOptions(SUPPLIER)
        }
    }

    private fun navigateToOptions(location: String) {
        if (loan == null)
            startActivity(
                Intent(this, OptionsActivity::class.java).apply {
                    putExtra(LOCATION, location)
                }
            )
        else
            startActivityForResult(
                Intent(this, FilterInventoryActivity::class.java).apply {
                    putExtra(LOCATION, location)
                    putExtra(ITEM_LOAN, loan)
                },
                LOANED
            )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(loan!=null && requestCode== LOANED && resultCode==SUCCESS) finish()
        super.onActivityResult(requestCode, resultCode, data)

    }

    companion object {
        const val APARTMENT = "Apartamento"
        const val LOCATION = "location"
        const val EXPO = "Exposicion"
        const val SUPPLIER = "Proveedores"
        const val LOANED = 420
        const val SUCCESS = 69
    }
}