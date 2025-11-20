package com.example.e_commerce

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce.api.RetrofitClient
import com.example.e_commerce.api.UserApi
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val imputCpf = findViewById<EditText>(R.id.inputCpf)
        val txtResultado = findViewById<TextView>(R.id.txtResultado)

        btnBuscar.setOnClickListener {
            val documento = imputCpf.text.toString().filter { it.isDigit() }
            if (documento.length != 11 && documento.length != 14){
                txtResultado.text = "Digite um CPF ou CNPJ válido."
                return@setOnClickListener
            }
            lifecycleScope.launch{
                try {
                    val api = RetrofitClient.instance.create(UserApi::class.java)
                    val user = api.getUserByDocumento(documento)
                    val endereco = user.address.firstOrNull()

                    val resultado = buildString {
                        appendLine("Nome: ${user.name}")
                        appendLine("Email: ${user.email}")
                        appendLine("Telefone: ${user.telefone}")
                        if (endereco != null) {
                            appendLine("Endereço: ${endereco.logradouro}, ${endereco.numero}")
                            appendLine("Bairro: ${endereco.bairro}")
                            appendLine("CEP: ${endereco.cep}")
                        }
                    }
                    txtResultado.text = resultado
                }catch (e: Exception){
                    txtResultado.text = "Erro ao buscar usuário: ${e.message}"
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}