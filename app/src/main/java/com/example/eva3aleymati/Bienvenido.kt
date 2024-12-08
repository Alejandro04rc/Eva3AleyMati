package com.example.eva3aleymati

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eva3aleymati.Vistas.ContactosFragment
import com.example.eva3aleymati.Vistas.FavoritosFragment
import com.example.eva3aleymati.Vistas.InicioFragment
import com.example.eva3aleymati.databinding.ActivityBienvenidoBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Bienvenido : AppCompatActivity() {

    // Inicializar viewBindign
    private lateinit var binding: ActivityBienvenidoBinding

    // Configurar firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //viewBinding
        binding = ActivityBienvenidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botón para ir al menú
        binding.btnIrAlMenu.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
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

        // Cargar fragments por defecto
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, InicioFragment()).commit()
        }

        // Configuración de navegación con el BottomNavigationView
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.item_1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, InicioFragment()).commit()
                    true
                }
                R.id.item_2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ContactosFragment()).commit()
                    true
                }
                R.id.item_3 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FavoritosFragment()).commit()
                    true
                }
                else -> false
            }
        }

        // Evitar recargar los fragmentos cuando se re-selecciona un ítem
        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId){
                R.id.item_1 -> {
                    true
                }
                R.id.item_2 -> {
                    true
                }
                R.id.item_3 -> {
                    true
                }
                else -> false
            }
        }
    }
    private fun signOut() {
        Firebase.auth.signOut() // Cierra la sesión del usuario en Firebase.
        Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_SHORT).show() // Muestra un mensaje de "Sesión cerrada".
        finish() // Finaliza la actividad actual.
    }
}