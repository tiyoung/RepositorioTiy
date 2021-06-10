package com.mv.listadecontatos.activity

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.mv.listadecontatos.R
import com.mv.listadecontatos.mvp.contract.EditarUsuarioContract
import com.mv.listadecontatos.mvp.presenter.EditarUsuarioPresenter
import com.mv.listadecontatos.utils.NetworkCheck

class EditarUsuarioActivity : AppCompatActivity(), EditarUsuarioContract.View {
    override lateinit var presenter: EditarUsuarioPresenter

    //Campos UI
    private var txt_email: EditText? = null
    private var txt_senha: EditText? = null
    private var btn_editarUsuario: Button? = null
    var progressBar: ProgressBar? = null

    //Variaveis
    var id_usuario: String = ""
    var email_usuario: String = ""
    var senha_usuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_usuario)

        presenter = EditarUsuarioPresenter(this)
        presenter.start()

        btn_editarUsuario!!.setOnClickListener {
            email_usuario = txt_email?.text.toString()
            senha_usuario = txt_senha?.text.toString()

            HideKeyBoard()
            if (networkCheck.isConnected()) {
                progressBar!!.isVisible = true
                presenter.editarUsuario(email_usuario.toString(), senha_usuario.toString() , id_usuario.toString())
            }
            else
            {
                displayErrorMessage("Verifique sua conexão")
            }
        }
    }

    private val networkCheck by lazy {
        NetworkCheck(ContextCompat.getSystemService(this, ConnectivityManager::class.java)!!)
    }

    override fun displayErrorMessage(mensagem: String) {
        progressBar!!.isVisible = false
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    override fun displaySucessToast(mensagem: String) {
        progressBar!!.isVisible = false
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    override fun bindViews() {
        val intent = intent
        id_usuario = intent.getStringExtra("idUsuario").toString()
        email_usuario = intent.getStringExtra("emailUsuario").toString()
        senha_usuario = intent.getStringExtra("senhaUsuario").toString()

        txt_email = findViewById<View>(R.id.txtLogin) as EditText
        txt_senha = findViewById<View>(R.id.txtSenha) as EditText
        btn_editarUsuario = findViewById<View>(R.id.btnEditarUsuario) as Button
        progressBar = findViewById<ProgressBar>(R.id.progressBarId) as ProgressBar

        txt_email!!.setText(email_usuario)
        txt_senha!!.setText(senha_usuario)
    }

    fun HideKeyBoard(){
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}