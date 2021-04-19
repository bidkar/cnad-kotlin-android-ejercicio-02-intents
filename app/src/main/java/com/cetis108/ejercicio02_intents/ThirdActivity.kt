package com.cetis108.ejercicio02_intents

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.cetis108.ejercicio02_intents.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    private val PHONE_CODE = 4000

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.menuContactos -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"))
                startActivity(intent)
            }
            R.id.menuMensaje -> {
                val intent = Intent().apply {
                    action = Intent.ACTION_SENDTO
                    data = Uri.parse("smsto:")
                    putExtra("sms_body", "Cuerpo del SMS desde un menú")
                }
                startActivity(intent)
            }
            R.id.menuVideo -> {
                val intent = Intent("android.media.action.VIDEO_CAPTURE")
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

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

        binding.buttonCallMe.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: 6871234567"))
            startActivity(intent)
        }

        binding.buttonCamera.setOnClickListener {
            //val intent = Intent(Intent.ACTION_CAMERA_BUTTON)
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            startActivity(intent)
        }

        binding.imageButtonPhone.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val phoneNumber = binding.editTextPhone.text.toString()
                if (phoneNumber.isNotEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checarPermiso(android.Manifest.permission.CALL_PHONE)) {
                            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel: 6871234567"))
                            if (ActivityCompat.checkSelfPermission(
                                    this@ThirdActivity,
                                    android.Manifest.permission.CALL_PHONE
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                return
                            }
                            startActivity(intent)
                        } else {
                            if (!shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)) {
                                requestPermissions(
                                    arrayOf(android.Manifest.permission.CALL_PHONE),
                                    PHONE_CODE
                                )
                            } else {
                                Toast.makeText(
                                    this@ThirdActivity,
                                    "Favor de habilitar los permisos",
                                    Toast.LENGTH_LONG
                                ).show()
                                val intentSettings =
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                intentSettings.addCategory(Intent.CATEGORY_DEFAULT)
                                intentSettings.data = Uri.parse("package:$packageName")
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                                startActivity(intentSettings)
                            }
                        }
                    } else {
                        versionAntigua(phoneNumber)
                    }
                } else {
                    Toast.makeText(
                        this@ThirdActivity,
                        "Debes ingresar un número, intenta de nuevo",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }

    fun checarPermiso(permission: String): Boolean {
        val result = this.checkCallingOrSelfPermission(permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun versionAntigua(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel: $phoneNumber"))
        if (checarPermiso(android.Manifest.permission.CALL_PHONE)) {
            if (ActivityCompat.checkSelfPermission(
                    this@ThirdActivity,
                    android.Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PHONE_CODE -> {
                val permiso = permissions[0]
                val resultado = grantResults[0]
                val editTextPhone = findViewById<EditText>(R.id.editTextPhone)

                if (permiso == android.Manifest.permission.CALL_PHONE) {
                    if (resultado == PackageManager.PERMISSION_GRANTED) {
                        val phoneNumber = editTextPhone.text.toString()
                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel: $phoneNumber"))
                        if (ActivityCompat.checkSelfPermission(
                                this,
                                android.Manifest.permission.CALL_PHONE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return
                        }
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Has denegado el permiso", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}