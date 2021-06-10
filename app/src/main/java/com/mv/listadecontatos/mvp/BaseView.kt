package com.mv.listadecontatos.mvp

interface BaseView<T> {
    var presenter : T
    fun bindViews()
}