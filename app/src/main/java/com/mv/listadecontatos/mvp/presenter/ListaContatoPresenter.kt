package com.mv.listadecontatos.mvp.presenter

import android.content.Context
import android.widget.ListView
import com.mv.listadecontatos.models.Contato
import com.mv.listadecontatos.mvp.contract.ListaContatoContract
import com.mv.listadecontatos.network.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaContatoPresenter(private val view : ListaContatoContract.View) :
    ListaContatoContract.Presenter {

    var contatos = ArrayList<Contato>()
    var lista: ListView? = null

    override fun listarContatos(email_usuario: String, contexto: Context) {
        val retrofitClient =
            NetworkUtils.getRetrofitInstance()
        val endpoint = retrofitClient.create(NetworkUtils.EndPointContacts::class.java)
        val callback = endpoint.getListarContatos(email_usuario)

        callback.enqueue(object : Callback<ArrayList<Contato>> {
            override fun onFailure(call: Call<ArrayList<Contato>>, t: Throwable) {
                view.displayErrorMessage(t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<Contato>>,
                response: Response<ArrayList<Contato>>
            ) {
                if (response.isSuccessful) {
                    contatos = response.body()!!;

                    if (contatos.size > 0) {
                        //var adapter: ListaAdapterService = ListaAdapterService(this@ListaContatosActivity, contatos)
                        //lista!!.adapter = adapter

                        //view.displayErrorMessage("OKkkkkk.".toString())
                        view.mostrarContatos(contatos)


                    } else {
                        view.displayErrorMessage("Nenhum contato encontrato.".toString())
                    }
                } else {
                    view.displayErrorMessage("Erro ao buscar contatos.".toString())
                }
            }
        })
    }

    override fun start() {
        view.bindViews()
    }
}