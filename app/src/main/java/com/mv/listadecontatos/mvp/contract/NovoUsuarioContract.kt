package com.mv.listadecontatos.mvp.contract

import com.mv.listadecontatos.mvp.BaseView
import com.mv.listadecontatos.mvp.BasePresenter
import com.mv.listadecontatos.mvp.presenter.NovoUsuarioPresenter

interface NovoUsuarioContract {
    interface View : BaseView<NovoUsuarioPresenter> {
        fun displayErrorMessage(mensagem: String)
        fun displaySucessToast(mensagem: String)

    }

    interface Presenter : BasePresenter {
        fun criarUsuario(email: String, senha: String)
    }
}