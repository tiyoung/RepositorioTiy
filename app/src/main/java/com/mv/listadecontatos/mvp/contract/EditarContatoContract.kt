package com.mv.listadecontatos.mvp.contract

import android.content.Context
import com.mv.listadecontatos.mvp.BaseView
import com.mv.listadecontatos.mvp.BasePresenter
import com.mv.listadecontatos.mvp.presenter.EditarContatoPresenter

interface EditarContatoContract {
    interface View : BaseView<EditarContatoPresenter> {
        fun displayErrorMessage(mensagem: String)
        fun displaySucessToast(mensagem: String)

    }

    interface Presenter : BasePresenter {
        fun editarContato(email_contato: String, nome: String, email_usuario: String, celular: String, id_contato: String)
        fun excluirContato(id_contato: String, contexto: Context)
    }
}