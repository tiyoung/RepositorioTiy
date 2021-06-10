package com.mv.listadecontatos.mvp.presenter

import android.content.Context
import com.mv.listadecontatos.models.Usuario
import com.mv.listadecontatos.mvp.contract.LoginContract
import com.mv.listadecontatos.network.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(private val view : LoginContract.View) : LoginContract.Presenter {

    override fun isLoginValid(login: String, senha: String) {
        if(login.isEmpty() || senha.isEmpty()) {
            view.displayErrorMessage("Informe o login e a senha do usuário")
        } else {
            val retrofitClient =
                NetworkUtils.getRetrofitInstance()
            val endpoint = retrofitClient.create(NetworkUtils.EndpointUser::class.java)
            val callback = endpoint.getDadosLogin(login, senha)

            callback.enqueue(object : Callback<List<Usuario>> {
                override fun onResponse(
                    call: Call<List<Usuario>>,
                    response: Response<List<Usuario>>
                ) {
                    if (response.isSuccessful) {
                        val code: Int = response.code()
                        val tamanho: Int = response.body()!!.size

                        if (code == 200 && tamanho > 0) {

                            response.body()?.forEach {
                                if (it.id != "") {
                                    view.startHomeActivity(it.id)
                                }
                            }
                        } else {
                            /*Toast.makeText(
                                baseContext,
                                "Dados incorretos ou usuário não existe.",
                                Toast.LENGTH_SHORT
                            ).show()*/
                            view.displayErrorMessage("Dados incorretos ou usuário não existe.".toString())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                    //Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
                    view.displayErrorMessage(t.message.toString())
                }
            })
        }
    }

    override fun start() {
        view.bindViews()
    }

}