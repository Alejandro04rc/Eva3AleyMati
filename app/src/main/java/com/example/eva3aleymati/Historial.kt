package com.example.eva3aleymati

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eva3aleymati.Adapter.AdapterViajes
import com.example.eva3aleymati.Models.Viajes
import com.example.eva3aleymati.databinding.ActivityHistorialBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Historial : AppCompatActivity() {

    // Inicializar viewBindign
    private lateinit var binding: ActivityHistorialBinding

    // Declarar FIREBASE DATABASE REALTIME
    private lateinit var database: DatabaseReference

    // Lista viajes
    private lateinit var viajesList : ArrayList<Viajes>

    // Declarar adaptador
    private lateinit var adapterViajes: AdapterViajes

    // Recycler view
    private lateinit var viajeRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //viewBinding
        binding = ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuramos el botón Menu
        binding.btnVolverMenu.setOnClickListener{
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        // Mostrar datos en la pantalla
        viajeRecyclerView = binding.rvViajes
        viajeRecyclerView.layoutManager = LinearLayoutManager(this) // Usamos LinearLayoutManager para organizar los elementos en una lista vertical
        viajeRecyclerView.hasFixedSize() // Indicamos que el tamaño del RecyclerView es fijo, lo cual mejora el rendimiento

        // Inicializamos la lista de viajes
        viajesList = arrayListOf<Viajes>()

        // Llamamos al método para obtener los viajes desde la base de datos
        getViajes()
    }

    // Método para obtener los viajes desde Firebase Realtime Database
    private fun getViajes() {
        database = FirebaseDatabase.getInstance().getReference("Viajes")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Verificamos si existen datos en la base de datos
                if(snapshot.exists()){
                    // Iteramos sobre cada viaje obtenido de la base de datos
                    for (viajesSnapshot in snapshot.children) {
                        val viaje = viajesSnapshot.getValue(Viajes::class.java) // Convertimos los datos en un objeto de tipo Viajes
                        viajesList.add(viaje!!) // Añadimos el viaje a la lista de viajes
                    }
                    // Configuramos el adaptador con la lista de viajes
                    adapterViajes = AdapterViajes(viajesList)
                    // Asignamos el adaptador al RecyclerView
                    viajeRecyclerView.adapter = adapterViajes
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}