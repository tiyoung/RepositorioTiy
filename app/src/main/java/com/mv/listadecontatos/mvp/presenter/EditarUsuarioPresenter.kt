package com.mv.listadecontatos.mvp.presenter

import com.mv.listadecontatos.models.Usuario
import com.mv.listadecontatos.models.UsuarioEditar
import com.mv.listadecontatos.mvp.contract.EditarUsuarioContract
import com.mv.listadecontatos.network.NetworkUtils
import com.mv.listadecontatos.utils.Validates.isEmailValid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarUsuarioPresenter(private val view : EditarUsuarioContract.View) :
    EditarUsuarioContract.Presenter {

    override fun editarUsuario(email: String, senha: String, id_usuario: String) {
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

            val userInfo = UsuarioEditar(
                id = id_usuario.toString(),
                email = email.toString(),
                password = senha.toString()
            )

            val callback = endpoint.putEditarUsuario(userInfo)

            callback.enqueue(object : Callback<List<Usuario>> {
                override fun onResponse(
                    call: Call<List<Usuario>>,
                    response: Response<List<Usuario>>
                ) {
                    if (response.isSuccessful) {
                        var code: Int = response.code()
                        var tamanho: Int = response.body()!!.size

                        if (code == 200 && tamanho > 0) {

                            response.body()?.forEach {
                                if (it.id.toString() != "") {
                                    view.displaySucessToast("Usuário alterado com sucesso.".toString())
                                }
                            }
                        } else {
                            view.displayErrorMessage("Erro ao editar usuário.".toString())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<List<Usuario>>,
                    t: Throwable
                ) {
                    view.displayErrorMessage("Erro ao editar usuário.".toString())
                }
            })
        }
    }

    override fun start() {
        view.bindViews()
    }
}