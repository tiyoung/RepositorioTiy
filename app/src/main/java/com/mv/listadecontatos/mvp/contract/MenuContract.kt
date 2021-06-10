package com.mv.listadecontatos.mvp.contract

import android.content.Context
import com.mv.listadecontatos.mvp.BaseView
import com.mv.listadecontatos.mvp.BasePresenter
import com.mv.listadecontatos.mvp.presenter.MenuPresenter

interface MenuContract {
    interface View : BaseView<MenuPresenter> {
        fun displayErrorMessage(mensagem: String)
        fun displaySucessToast(mensagem: String)
        //fun startHomeActivity()
    }

    interface Presenter : BasePresenter {
        fun excluirUsuario(id_usuario: String, context: Context)
        fun Logout(context: Context)
    }
}