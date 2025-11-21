package com.example.e_commerce

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.api.RetrofitClient
import com.example.e_commerce.api.UserApi
import com.example.e_commerce.model.UserPresenter
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val inputCpf = findViewById<EditText>(R.id.inputCpf)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val cardResultado = findViewById<View>(R.id.cardResultado)

        val presenter = UserPresenter(
            cardResultado,
            findViewById(R.id.txtNome),
            findViewById(R.id.txtCpf),
            findViewById(R.id.txtEmail),
            findViewById(R.id.txtTelefone),
            findViewById(R.id.txtIdEndereco),
            findViewById(R.id.txtLogradouro),
            findViewById(R.id.txtNumero),
            findViewById(R.id.txtComplemento),
            findViewById(R.id.txtBairro),
            findViewById(R.id.txtCep)
        )

        btnBuscar.setOnClickListener {
            val documento = inputCpf.text.toString().filter { it.isDigit() }
            if (documento.length != 11 && documento.length != 14){
                Toast.makeText(this, "Digite um CPF ou CNPJ válido.",
                    Toast.LENGTH_SHORT).show()
                cardResultado.visibility = View.GONE
                return@setOnClickListener
            }
            btnBuscar.isEnabled = false
            progressBar.visibility = View.VISIBLE

            presenter.buscarUsuario(documento, lifecycleScope, this)
            btnBuscar.isEnabled = true
            progressBar.visibility = View.GONE
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}