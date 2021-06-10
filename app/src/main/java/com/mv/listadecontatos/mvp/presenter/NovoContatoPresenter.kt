package com.mv.listadecontatos.mvp.presenter

import android.text.TextUtils
import com.mv.listadecontatos.models.*
import com.mv.listadecontatos.mvp.contract.NovoContatoContract
import com.mv.listadecontatos.network.NetworkUtils
import com.mv.listadecontatos.utils.Validates.isEmailValid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovoContatoPresenter(private val view : NovoContatoContract.View) :
    NovoContatoContract.Presenter {

    override fun criarContato(email_contato: String, nome: String, email_usuario: String, celular: String) {
        if (TextUtils.isEmpty(nome)) {
            view.displayErrorMessage("Informe o nome do contato.".toString())
        } else if(TextUtils.isEmpty(email_contato)){
            view.displayErrorMessage("Informe o e-mail do contato.".toString())
        } else if(TextUtils.isEmpty(celular)){
            view.displayErrorMessage("Informe o celular do contato.".toString())
        }
        else if(!email_contato.toString().isEmailValid()){
            view.displayErrorMessage("E-mail inválido.".toString())
        }
        else if(celular.toString().length < 13){
            view.displayErrorMessage("Telefone inválido.".toString())
        }
        else
        {
            val retrofitClient =
                NetworkUtils.getRetrofitInstance()
            val endpoint = retrofitClient.create(NetworkUtils.EndPointContacts::class.java)

            val contatoInfo = ContatoNovo(
                email_contato = email_contato!!,
                nome = nome!!,
                email_usuario = email_usuario!!,
                celular = celular!!
            )

            val callback = endpoint.postNovoContato(contatoInfo)

            callback.enqueue(object : Callback<Contato> {
                override fun onFailure(call: Call<Contato>, t: Throwable) {
                    view.displayErrorMessage("Erro ao cadastrar contato.".toString())
                }

                override fun onResponse(
                    call: Call<Contato>,
                    response: Response<Contato>
                ) {
                    if (response.isSuccessful) {
                        view.displaySucessToast("Contato Cadastrado!".toString())
                    } else {
                        view.displaySucessToast("Erro ao Cadastrar contato.".toString())
                    }
                }
            })
        }
    }

    override fun start() {
        view.bindViews()
    }
}