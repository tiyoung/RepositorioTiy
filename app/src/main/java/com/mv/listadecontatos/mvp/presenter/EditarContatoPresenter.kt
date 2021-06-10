package com.mv.listadecontatos.mvp.presenter

import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import com.mv.listadecontatos.models.*
import com.mv.listadecontatos.mvp.contract.EditarContatoContract
import com.mv.listadecontatos.network.NetworkUtils
import com.mv.listadecontatos.utils.Validates.isEmailValid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarContatoPresenter(private val view : EditarContatoContract.View) :
    EditarContatoContract.Presenter {

    override fun editarContato(email_contato: String, nome_contato: String, email_usuario: String, celular_contato: String, id_contato: String) {
        if (TextUtils.isEmpty(nome_contato)) {
            view.displayErrorMessage("Informe o nome do contato.".toString())
        } else if(TextUtils.isEmpty(email_contato)){
            view.displayErrorMessage("Informe o e-mail do contato.".toString())
        } else if(TextUtils.isEmpty(celular_contato)){
            view.displayErrorMessage("Informe o celular do contato.".toString())
        }
        else if(!email_contato.toString().isEmailValid()){
            view.displayErrorMessage("E-mail inválido.".toString())
        }
        else if(celular_contato.toString().length < 13){
            view.displayErrorMessage("Telefone inválido.".toString())
        }
        else
        {
            val retrofitClient =
                NetworkUtils.getRetrofitInstance()
            val endpoint = retrofitClient.create(NetworkUtils.EndPointContacts::class.java)

            val contatoInfo = ContatoEditar(
                id = id_contato!!,
                nome = nome_contato!!,
                email_usuario = email_usuario!!,
                email_contato = email_contato!!,
                celular = celular_contato!!
            )

            val callback = endpoint.putEditarContato(contatoInfo)

            callback.enqueue(object : Callback<ArrayList<Contato>> {
                override fun onResponse(
                    call: Call<ArrayList<Contato>>,
                    response: Response<ArrayList<Contato>>
                ) {
                    if (response.isSuccessful) {
                        var code: Int = response.code()
                        var tamanho: Int = response.body()!!.size

                        if (code == 200 && tamanho > 0) {
                            view.displaySucessToast("Contato editado com sucesso.".toString())
                        } else {
                            view.displayErrorMessage("Erro ao editar contato.".toString())
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<Contato>>,
                    t: Throwable
                ) {
                    view.displayErrorMessage("Erro ao editar contato." + t.message.toString())
                }
            })
        }
    }

    override fun excluirContato(id_contato: String, contexto:Context) {
        val builder = AlertDialog.Builder(contexto)
        builder.setTitle("Confirmar")
        builder.setMessage("Deseja excluir esse contato?")

        builder.setPositiveButton(
            "SIM"
        ) { dialog, which -> // Do nothing but close the dialog
            dialog.dismiss()

            val retrofitClient = NetworkUtils.getRetrofitInstance()
            val endpoint = retrofitClient.create(NetworkUtils.EndPointContacts::class.java)

            val usuarioInfo = ContatoExcluir(
                id = id_contato!!
            )

            val callback = endpoint.deleteContato(usuarioInfo)

            callback.enqueue(object : Callback<Contato>{
                override fun onResponse(
                    call: Call<Contato>,
                    response: Response<Contato>
                ) {
                    if(response.isSuccessful){
                        view.displaySucessToast("Contato excluído com sucesso!")
                    }
                    else{

                        var code: Int = response.code()
                        println("**********"+response.code())
                        println("**********"+response.message())

                        if(code == 500){
                            view.displayErrorMessage("Erro ao excluir contato.")
                        }
                        else{
                            //Sem permissão
                        }
                    }
                }

                override fun onFailure(call: Call<Contato>, t: Throwable) {
                    view.displayErrorMessage("Erro ao editar usuário.")
                }
            })
        }

        builder.setNegativeButton(
            "NÃO"
        ) { dialog, which -> // Do nothing
            dialog.dismiss()
            view.displayErrorMessage("")
        }

        val alert = builder.create()
        alert.show()
    }

    override fun start() {
        view.bindViews()
    }
}