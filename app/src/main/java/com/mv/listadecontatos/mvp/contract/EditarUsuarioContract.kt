package com.mv.listadecontatos.mvp.contract

import com.mv.listadecontatos.mvp.BaseView
import com.mv.listadecontatos.mvp.BasePresenter
import com.mv.listadecontatos.mvp.presenter.EditarUsuarioPresenter

interface EditarUsuarioContract {
    interface View : BaseView<EditarUsuarioPresenter> {
        fun displayErrorMessage(mensagem: String)
        fun displaySucessToast(mensagem: String)

    }

    interface Presenter : BasePresenter {
        fun editarUsuario(email: String, senha: String, id_usuario: String)
    }
}