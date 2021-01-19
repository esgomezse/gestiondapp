package com.umb.gestiondapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umb.gestiondapp.LocationsActivity.Companion.LOCATION
import kotlinx.android.synthetic.main.options.*


/**
 * Created By Juan Felipe Arango on 11/11/20
 * Copyright ©2020 Merqueo. All rights reserved
 */

class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.options)
        cnlUploadHelp.setOnClickListener {
            startActivity(
                Intent(this, UploadProduct::class.java).apply {
                    putExtra(LOCATION, intent.getStringExtra(LOCATION))
                }
            )
        }
        cnlInventory.setOnClickListener {
            startActivity(
                Intent(this, FilterInventoryActivity::class.java).apply {
                    putExtra(LOCATION, intent.getStringExtra(LOCATION))
                }
            )
        }
    }
}