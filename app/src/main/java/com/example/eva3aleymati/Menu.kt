package com.example.eva3aleymati

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eva3aleymati.databinding.ActivityMenuBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Menu : AppCompatActivity() {

    // Configuracion de viewBinding
    private lateinit var binding: ActivityMenuBinding

    // Configurar firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar viewBinding
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar firebase auth
        auth = Firebase.auth

        // Este es el botón para cerrar sesión.
        binding.btnLogout.setOnClickListener{
            // Mostramos un cuadro de diálogo que pregunta al usuario si está seguro de cerrar sesión.
            MaterialAlertDialogBuilder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Estas seguro que deseas cerrar sesión?")
                .setNeutralButton("Cancelar") { dialog, which ->
                    // Si el usuario cancela, no hacemos nada.
                }
                .setPositiveButton("Aceptar") { dialog, which ->
                    signOut() // Cerrar sesión
                    val intent = Intent(this, MainActivity::class.java) // Redirigimos al usuario a la pantalla de inicio
                    startActivity(intent)
                    finish() // Finalizamos la actividad actual
                }
                .show() // Mostramos el cuadro de diálogo.
        }

        // Configurar el boton Realizar Viaje
        binding.btnRealizarViaje.setOnClickListener{
            val intent = Intent(this, RealizarViaje::class.java)
            startActivity(intent)
        }
        // Configurar el boton Historial
        binding.btnHistorial.setOnClickListener{
            val intent = Intent(this, Historial::class.java)
            startActivity(intent)
        }
        // Configurar el boton Bienvenido
        binding.btnVolverInicio.setOnClickListener{
            val intent = Intent(this, Bienvenido::class.java)
            startActivity(intent)
        }
    }

    // Esta función cierra la sesión del usuario en Firebase y muestra un mensaje de confirmación.
    private fun signOut() {
        Firebase.auth.signOut() // Cierra la sesión del usuario en Firebase.
        Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_SHORT).show() // Muestra un mensaje de "Sesión cerrada".
        finish() // Finaliza la actividad actual.
    }
}