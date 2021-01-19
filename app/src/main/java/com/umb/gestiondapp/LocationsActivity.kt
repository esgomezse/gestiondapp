package com.umb.gestiondapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.locations.*


/**
 * Created By Juan Felipe Arango on 11/11/20
 * Copyright ©2020 Merqueo. All rights reserved
 */
class LocationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.locations)
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
        startActivity(
            Intent(this, OptionsActivity::class.java).apply {
                putExtra(LOCATION, location)
            }
        )
    }

    companion object {
        const val APARTMENT = "Apartamento"
        const val LOCATION = "location"
        const val EXPO = "Exposicion"
        const val SUPPLIER = "Proveedores"
    }
}