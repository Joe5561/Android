package com.example.e_commerce

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
        val cardResultado = findViewById<View>(R.id.cardResultado)

        val txtNome = findViewById<TextView>(R.id.txtNome)
        val txtCpf = findViewById<TextView>(R.id.txtCpf)
        val txtEmail = findViewById<TextView>(R.id.txtEmail)
        val txtTelefone = findViewById<TextView>(R.id.txtTelefone)

        val txtIdEndereco = findViewById<TextView>(R.id.txtIdEndereco)
        val txtLogradouro = findViewById<TextView>(R.id.txtLogradouro)
        val txtNumero = findViewById<TextView>(R.id.txtNumero)
        val txtComplemento = findViewById<TextView>(R.id.txtComplemento)
        val txtBairro = findViewById<TextView>(R.id.txtBairro)
        val txtCep = findViewById<TextView>(R.id.txtCep)

        btnBuscar.setOnClickListener {
            val documento = imputCpf.text.toString().filter { it.isDigit() }
            if (documento.length != 11 && documento.length != 14) {
                Toast.makeText(this, "Digite um CPF ou CNPJ válido.", Toast.LENGTH_SHORT).show()
                cardResultado.visibility = View.GONE
                return@setOnClickListener
            }
            lifecycleScope.launch{
                try {
                    val api = RetrofitClient.instance.create(UserApi::class.java)
                    val user = api.getUserByDocumento(documento)
                    val endereco = user.address.firstOrNull()
                    txtNome.text = "Nome: ${user.name}"
                    txtCpf.text = "CPF: ${user.cpf}"
                    txtEmail.text = "Email: ${user.email}"
                    txtTelefone.text = "Telefone: ${user.telefone}"

                    if (endereco != null) {
                        txtIdEndereco.text = "Endereço: ${endereco.logradouro}"
                        txtLogradouro.text = "Logradouro: ${endereco.logradouro}"
                        txtNumero.text = "Número: ${endereco.numero}"
                        txtComplemento.text = "Complemento: ${endereco.complemento}"
                        txtBairro.text = "Bairro: ${endereco.bairro}"
                        txtCep.text = "CEP: ${endereco.cep}"
                    } else {
                        txtIdEndereco.text = "Endereço: não informado"
                        txtComplemento.text = ""
                        txtCep.text = ""
                    }
                    cardResultado.alpha = 0f
                    cardResultado.visibility = View.VISIBLE
                    cardResultado.animate().alpha(1f).setDuration(300).start()
                }catch (e: Exception){
                    cardResultado.visibility = View.GONE
                    Toast.makeText(this@MainActivity,
                        "Erro ao buscar usuário: ${e.message}",
                        Toast.LENGTH_SHORT).show()
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