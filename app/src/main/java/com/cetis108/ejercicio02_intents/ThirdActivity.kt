package com.cetis108.ejercicio02_intents

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.webkit.URLUtil
import android.widget.Toast
import com.cetis108.ejercicio02_intents.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_third)

        val binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButtonWeb.setOnClickListener {
            val url = "https://" + binding.editTextWeb.text.toString()
            if (URLUtil.isValidUrl(url) && Patterns.WEB_URL.matcher(url).matches()) {
                val intentWeb = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse("$url")
                }
                startActivity(intentWeb)
            } else {
                Toast.makeText(this, "Dirección errónea!", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonEmailMe.setOnClickListener {
            val email = "bidkar@cetis108.edu.mx"
            val asunto = "Titulo del email"
            val mensaje = "Hola estoy esperando la respuesta"
            val direcciones = arrayOf("uno@numero.com", "dos@numero.com", "tres@numero.com")
            val intentEmail = Intent().apply {
                action = Intent.ACTION_SEND
                data = Uri.parse(email)
                putExtra(Intent.EXTRA_SUBJECT, asunto)
                putExtra(Intent.EXTRA_TEXT, mensaje)
                putExtra(Intent.EXTRA_EMAIL, direcciones)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intentEmail, "Elige el cliente de correo:"))
        }
    }
}