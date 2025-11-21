package com.example.e_commerce.model

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.e_commerce.api.RetrofitClient
import com.example.e_commerce.api.UserApi
import kotlinx.coroutines.launch

class UserPresenter(
    private val cardResultado: View,
    private val txtNome: TextView,
    private val txtCpf: TextView,
    private val txtEmail: TextView,
    private val txtTelefone: TextView,
    private val txtIdEndereco: TextView,
    private val txtLogradouro: TextView,
    private val txtNumero: TextView,
    private val txtComplemento: TextView,
    private val txtBairro: TextView,
    private val txtCep: TextView
){
    fun buscarUsuario(documento: String,
                      scope: LifecycleCoroutineScope,
                      context: android.content.Context,
                      onFinish: () -> Unit = {}) {
        scope.launch {
            try {
                val api = RetrofitClient.instance.create(UserApi::class.java)
                val user = api.getUserByDocumento(documento)
                val endereco = user.address.firstOrNull()

                // Preenche dados do usuário
                txtNome.text = "Nome: ${user.name}"
                txtCpf.text = "CPF: ${user.cpf}"
                txtEmail.text = "Email: ${user.email}"
                txtTelefone.text = "Telefone: ${user.telefone}"

                // Preenche dados do endereço
                if (endereco != null) {
                    txtIdEndereco.text = "ID Endereço: ${endereco.id}"
                    txtLogradouro.text = "Logradouro: ${endereco.logradouro}"
                    txtNumero.text = "Número: ${endereco.numero}"
                    txtComplemento.text = "Complemento: ${endereco.complemento}"
                    txtBairro.text = "Bairro: ${endereco.bairro}"
                    txtCep.text = "CEP: ${endereco.cep}"
                } else {
                    txtIdEndereco.text = "Endereço: não informado"
                    txtLogradouro.text = ""
                    txtNumero.text = ""
                    txtComplemento.text = ""
                    txtBairro.text = ""
                    txtCep.text = ""
                }
                // Exibe card com animação
                cardResultado.alpha = 0f
                cardResultado.visibility = View.VISIBLE
                cardResultado.animate().alpha(1f).setDuration(300).start()
            } catch (e: Exception) {
                cardResultado.visibility = View.GONE
                Toast.makeText(context, "Erro ao buscar usuário: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                onFinish()
            }
        }
    }
}