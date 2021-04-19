package com.cetis108.ejercicio02_intents

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.cetis108.ejercicio02_intents.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    val saludo = "Hola desde el MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        val btn_siguiente = findViewById<Button>(R.id.btnSiguiente)
        val btn_calcular = findViewById<Button>(R.id.btnCalcular)

        btn_siguiente.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("saludo", saludo)
            startActivity(intent)
        }

        btn_calcular.setOnClickListener {
            val añoNac: Int = findViewById<TextView>(R.id.editTextNumber).text.toString().toInt()
            val añoActual = Calendar.getInstance().get(Calendar.YEAR)
            val miEdad = añoActual - añoNac
            val txt_textView2 = findViewById<TextView>(R.id.textView2)

            txt_textView2.text = "Tu edad es $miEdad años"
        }

        val btn_third = findViewById<Button>(R.id.btnThird)

        btn_third.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        // esto se puede gracias a kotlin-android-extensions, se debe agregar a build.gradle
        //btnCalcular.setOnClickListener {}

        /*val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalcular.setOnClickListener {
            val añoNac: Int = binding.editTextNumber.text.toString().toInt()
            val añoActual = Calendar.getInstance().get(Calendar.YEAR)
            val miEdad = añoActual - añoNac

            binding.textView2.text = "Tu edad es $miEdad años"
        }

        binding.btnSiguiente.setOnClickListener {
            startActivity(this, SecondActivity::class.java)
        }
    }

    fun startActivity(activity: Activity, nextActivity: Class<*>) {
        val intent = Intent(activity, nextActivity)
        intent.putExtra("saludo", saludo)
        activity.startActivity(intent)
        activity.finish()
        */
    }
}