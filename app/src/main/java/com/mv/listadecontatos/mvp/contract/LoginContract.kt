package com.mv.listadecontatos.mvp.contract

import com.mv.listadecontatos.mvp.BaseView
import com.mv.listadecontatos.mvp.BasePresenter
import com.mv.listadecontatos.mvp.presenter.LoginPresenter

interface LoginContract {
    interface View : BaseView<LoginPresenter> {
        fun displayErrorMessage(mensagem: String)
        fun displaySucessToast(mensagem: String)
        fun startHomeActivity(usuario_id: String)
    }

    interface Presenter : BasePresenter {
        fun isLoginValid(userName: String, password : String)
    }
}