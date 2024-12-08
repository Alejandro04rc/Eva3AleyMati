package com.example.eva3aleymati

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eva3aleymati.Models.Viajes
import com.example.eva3aleymati.databinding.ActivityRealizarViajeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RealizarViaje : AppCompatActivity() {

    // Activar viewBinding
    private  lateinit var binding: ActivityRealizarViajeBinding

    // Activar firebase DATABASE REALTIME
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar viewBinding
        binding = ActivityRealizarViajeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Iniciar la base de datos
        database = FirebaseDatabase.getInstance().getReference("Viajes")

        // Este botón maneja el clic para agregar un nuevo viaje a la base de datos.
        binding.btnHacerViaje.setOnClickListener{
            // Obtener los datos
            val origen = binding.etOrigen.text.toString()
            val destino = binding.etDestino.text.toString()
            val descripcion = binding.etDescripcionViaje.text.toString()
            // Generar el id random
            val id = database.child("Viajes").push().key

            // Verificamos si los campos de texto están vacíos y mostramos un error si es necesario.
            if (origen.isEmpty()){
                binding.etOrigen.error = "Porfavor ingresar origen"
                return@setOnClickListener
            }
            if (destino.isEmpty()){
                binding.etDestino.error = "Porfavor ingresar destino"
                return@setOnClickListener
            }
            if (descripcion.isEmpty()){
                binding.etDescripcionViaje.error = "Porfavor ingresar descripcion"
                return@setOnClickListener
            }

            // Creamos un objeto 'Viaje' con los datos obtenidos
            val viaje = Viajes(id, origen, destino, descripcion)

            // Guardamos el viaje en la base de datos de Firebase.
            database.child(id!!).setValue(viaje)
                .addOnSuccessListener {
                    // Limpiamos los campos de texto después de que se haya guardado el viaje.
                    binding.etOrigen.setText("")
                    binding.etDestino.setText("")
                    binding.etDescripcionViaje.setText("")
                    // Mostramos un mensaje de éxito usando un Snackbar.
                    Snackbar.make(binding.root, "Viaje Agregado", Snackbar.LENGTH_SHORT).show()
                }
        }

        // Este botón permite al usuario ver el historial de viajes realizados.
        binding.btnVer.setOnClickListener{
            val intent = Intent(this, Historial::class.java)
            startActivity(intent)
        }

        // Este botón permite al usuario volver al menú principal.
        binding.btnVolverMenu.setOnClickListener{
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

    }
}