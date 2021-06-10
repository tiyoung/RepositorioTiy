package com.mv.listadecontatos.activity

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import com.mv.listadecontatos.R
import com.mv.listadecontatos.mvp.contract.LoginContract
import com.mv.listadecontatos.mvp.presenter.LoginPresenter
import com.mv.listadecontatos.utils.NetworkCheck

class LoginActivity : AppCompatActivity(), LoginContract.View {

    override lateinit var presenter: LoginPresenter

    //Campos UI
    private var btn_novo_usuario: Button? = null
    private var btn_logar: Button? = null
    private var txt_email: EditText? = null
    private var txt_senha: EditText? = null
    var progressBar: ProgressBar? = null

    //Variaveis
    private var id_usuario: String? = null
    private var email_usuario: String? = null
    private var senha_usuario: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
        presenter.start()

        btn_novo_usuario!!.setOnClickListener {
            HideKeyBoard()
            val intent = Intent(this, NovoUsuarioActivity::class.java)
            startActivity(intent)
        }

        btn_logar!!.setOnClickListener {
            HideKeyBoard()
            email_usuario = txt_email?.text.toString()
            senha_usuario = txt_senha?.text.toString()

            if(networkCheck.isConnected()){

                progressBar!!.isVisible = true

                presenter.isLoginValid(email_usuario.toString(), senha_usuario.toString())
            }
            else{
                displayErrorMessage("Verifique sua conexão")
            }
        }
    }

    private val networkCheck by lazy {
        NetworkCheck(getSystemService(this, ConnectivityManager::class.java)!!)
    }

    override fun displayErrorMessage(mensagem: String) {
        progressBar!!.isVisible = false
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    override fun displaySucessToast(mensagem: String) {
        progressBar!!.isVisible = false
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    override fun startHomeActivity(usuario_id: String) {
        progressBar!!.isVisible = false
        val intent = Intent(applicationContext, MenuActivity::class.java)
        intent.putExtra("idUsuario", usuario_id)
        intent.putExtra("emailUsuario", email_usuario)
        intent.putExtra("senhaUsuario", senha_usuario)
        startActivity(intent)
    }

    override fun bindViews() {
        txt_email = findViewById<View>(R.id.txtEmail) as EditText
        txt_senha = findViewById<View>(R.id.txtSenha) as EditText
        btn_novo_usuario = findViewById<View>(R.id.btnNovoUsuario) as Button
        btn_logar = findViewById<View>(R.id.btnLogar) as Button
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