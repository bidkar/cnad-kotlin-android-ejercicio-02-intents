package com.cetis108.ejercicio02_intents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.cetis108.ejercicio02_intents.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        supportActionBar!!.title = "Regresar"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val txt = findViewById<TextView>(R.id.textViewIntent)
        if (intent.getStringExtra("saludo") != null) {
            txt.text = intent.getStringExtra("saludo").toString()
        } else {
            txt.text = "La variable saludo no llegó desde MainActivity"
            Toast.makeText(this,"La variable saludo no llegó desde MainActivity",Toast.LENGTH_LONG).show()
        }

        /*val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle != null && bundle.getString("saludo") != null) {
            binding.textViewIntent.text = bundle.getString("saludo")
        } else {
            binding.textViewIntent.text = "La variable saludo no llegó desde MainActivity"
        }*/
    }
}