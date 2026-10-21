package com.example.salasmart

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var tvSalas: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSalas = findViewById(R.id.tvSalas)

        // Conectar a Firebase
        database = FirebaseDatabase.getInstance().getReference("salas")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val salasTexto = StringBuilder()

                for (sala in snapshot.children) {
                    val nombreSala = sala.key ?: "Sala"
                    val estado = sala.child("estado").getValue(String::class.java) ?: "Sin estado"
                    salasTexto.append("$nombreSala: $estado\n")
                }

                if (salasTexto.isEmpty()) {
                    tvSalas.text = "No hay salas registradas."
                } else {
                    tvSalas.text = salasTexto.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                tvSalas.text = "Error: ${error.message}"
            }
        })
    }
}