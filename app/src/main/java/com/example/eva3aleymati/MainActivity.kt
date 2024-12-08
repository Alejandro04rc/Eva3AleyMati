package com.example.eva3aleymati

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eva3aleymati.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    // Configuracion de viewBinding
    private lateinit var binding: ActivityMainBinding

    // Configurar firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar Firebase auth
        auth = Firebase.auth

        // Programar el botob de login
        binding.btnLogin.setOnClickListener{

            val email = binding.etEmail.text.toString() // Obtenemos el email ingresado.
            val password = binding.etPassword.text.toString() // Obtenemos la contraseña ingresada.

            // Verificamos que los campos no estén vacíos.
            if (email.isEmpty()){
                binding.etEmail.error = "Por favor ingrese un correo"
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.etPassword.error = "Por favor ingrese la contraseña"
                return@setOnClickListener
            }
            // Si los campos son válidos, intentamos iniciar sesión.
            signIn(email, password)
        }
    }

    // Función para realizar el inicio de sesión con Firebase.
    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    // Si el inicio de sesión es exitoso, mostramos un mensaje de éxito.
                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()

                    // Intentamos redirigir al usuario a la pantalla principal (Bienvenido).
                    try {
                        val intent = Intent(this, Bienvenido::class.java)
                        startActivity(intent)
                    } catch (e: Exception) {
                        // Si ocurre un error al redirigir, mostramos un mensaje con el error.
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }


                }else{
                    // Si el inicio de sesión falla, mostramos un mensaje de error.
                    Toast.makeText(this, "Error al inciar sesión", Toast.LENGTH_SHORT).show()
                }
            }

    }
}