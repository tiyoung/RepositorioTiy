package com.mv.listadecontatos.mvp.presenter

import com.mv.listadecontatos.models.Usuario
import com.mv.listadecontatos.models.UsuarioNovo
import com.mv.listadecontatos.mvp.contract.NovoUsuarioContract
import com.mv.listadecontatos.network.NetworkUtils
import com.mv.listadecontatos.utils.Validates.isEmailValid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovoUsuarioPresenter(private val view : NovoUsuarioContract.View) :
    NovoUsuarioContract.Presenter {

    override fun criarUsuario(email: String, senha: String) {
        if(email.isEmpty() || senha.isEmpty()) {
            view.displayErrorMessage("Informe o login e a senha do usuário")
        }
        else if(!email.toString().isEmailValid()){
            view.displayErrorMessage("E-mail inválido")
        }
        else {
            val retrofitClient =
                NetworkUtils.getRetrofitInstance()
            val endpoint = retrofitClient.create(NetworkUtils.EndpointUser::class.java)

            val userInfo = UsuarioNovo(
                email = email.toString(),
                password = senha.toString()
            )

            val callback = endpoint.postNovoUsuario(userInfo)

            callback.enqueue(object : Callback<Usuario> {
                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    view.displayErrorMessage("Erro ao cadastrar usuário".toString())
                }

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        view.displaySucessToast("Usuário Cadastrado com sucesso!".toString())
                    } else {

                        var code: Int = response.code()
                        println("**********" + response.code())
                        println("**********" + response.message())

                        if (code == 500) {
                            view.displayErrorMessage("Erro ao Cadastrar usuário, login ou senha ja existem.".toString())
                        } else {

                        }
                    }
                }
            })
        }
    }

    override fun start() {
        view.bindViews()
    }
}