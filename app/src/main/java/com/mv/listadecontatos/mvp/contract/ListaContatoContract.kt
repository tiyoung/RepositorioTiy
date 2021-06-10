package com.mv.listadecontatos.mvp.contract

import android.content.Context
import com.mv.listadecontatos.models.Contato
import com.mv.listadecontatos.mvp.BaseView
import com.mv.listadecontatos.mvp.BasePresenter
import com.mv.listadecontatos.mvp.presenter.ListaContatoPresenter

interface ListaContatoContract {
    interface View : BaseView<ListaContatoPresenter> {
        fun displayErrorMessage(mensagem: String)
        fun displaySucessToast(mensagem: String)
        fun startActivityNew()
        fun mostrarContatos(contatos: ArrayList<Contato>)
    }

    interface Presenter : BasePresenter {
        fun listarContatos(email_usuario: String, contexto: Context)
    }
}