package com.umb.gestiondapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.inventario.*

class MacroActivity : AppCompatActivity() {

    private val database = Firebase.database
    private var myRef: DatabaseReference = database.getReference("Activo")
    private val micros = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inventario)
        initListeners()
    }

    private fun initFirebaseListener() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                micros.clear()
                dataSnapshot.children.forEach {
                    micros.add(it.key.toString())
                }
                setMicros()
            }

            override fun onCancelled(error: DatabaseError) {

                print(error.toException())
            }
        })
    }

    private fun setMicros() {
        spnMicro.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            micros
        )
    }

    private fun initListeners() {
        spnMacro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                myRef = if (position == 0) database.getReference("Activo")
                else database.getReference("Pasivo")
                initFirebaseListener()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
}