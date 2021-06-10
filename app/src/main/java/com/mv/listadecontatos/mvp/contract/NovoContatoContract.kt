package com.mv.listadecontatos.mvp.contract

import com.mv.listadecontatos.mvp.BaseView
import com.mv.listadecontatos.mvp.BasePresenter
import com.mv.listadecontatos.mvp.presenter.NovoContatoPresenter

interface NovoContatoContract {
    interface View : BaseView<NovoContatoPresenter> {
        fun displayErrorMessage(mensagem: String)
        fun displaySucessToast(mensagem: String)

    }

    interface Presenter : BasePresenter {
        fun criarContato(email_contato: String, nome: String, email_usuario: String, celular: String)
    }
}