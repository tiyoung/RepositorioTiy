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
import com.mv.listadecontatos.mvp.contract.NovoUsuarioContract
import com.mv.listadecontatos.mvp.presenter.NovoUsuarioPresenter
import com.mv.listadecontatos.utils.NetworkCheck

class NovoUsuarioActivity : AppCompatActivity(), NovoUsuarioContract.View  {
    override lateinit var presenter: NovoUsuarioPresenter

    //Campos UI
    private var txt_email: EditText? = null
    private var txt_senha: EditText? = null
    private var btn_cadastrarUsuario: Button? = null
    var progressBar: ProgressBar? = null

    //Variaveis
    private var email: String? = null
    private var senha: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_usuario)

        presenter = NovoUsuarioPresenter(this)
        presenter.start()

        btn_cadastrarUsuario!!.setOnClickListener {
            email = txt_email?.text.toString()
            senha = txt_senha?.text.toString()

            HideKeyBoard()
            if (networkCheck.isConnected()) {
                progressBar!!.isVisible = true
                presenter.criarUsuario(email.toString(), senha.toString())
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
        txt_email!!.setText("")
        txt_email!!.requestFocus()
        txt_senha!!.setText("")
    }

    override fun bindViews() {
        txt_email = findViewById<View>(R.id.txtLogin) as EditText
        txt_senha = findViewById<View>(R.id.txtSenha) as EditText
        btn_cadastrarUsuario = findViewById<View>(R.id.btnCadastrarUsuario) as Button
        progressBar = findViewById<ProgressBar>(R.id.progressBarId) as ProgressBar
    }

    fun HideKeyBoard(){
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}