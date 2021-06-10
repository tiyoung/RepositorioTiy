package com.mv.listadecontatos.activity

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.mv.listadecontatos.R
import com.mv.listadecontatos.mvp.contract.MenuContract
import com.mv.listadecontatos.mvp.presenter.MenuPresenter
import com.mv.listadecontatos.utils.NetworkCheck

class MenuActivity : AppCompatActivity(), MenuContract.View {
    override lateinit var presenter: MenuPresenter

    private var btn_listar_contatos: Button? = null
    private var btn_editar_usuario: Button? = null
    private var btn_excluir_usuario: Button? = null
    private var btn_logout: Button? = null
    var progressBar: ProgressBar? = null

    var id_usuario: String = ""
    var email_usuario: String = ""
    var senha_usuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        presenter = MenuPresenter(this)
        presenter.start()

        btn_listar_contatos!!.setOnClickListener {
            val intent = Intent(applicationContext, ListaContatosActivity::class.java)
            intent.putExtra("emailUsuario", email_usuario)
            startActivity(intent)
        }

        btn_editar_usuario!!.setOnClickListener {
            val intent = Intent(applicationContext, EditarUsuarioActivity::class.java)
            intent.putExtra("idUsuario", id_usuario)
            intent.putExtra("emailUsuario", email_usuario)
            intent.putExtra("senhaUsuario", senha_usuario)
            startActivity(intent)
        }

        btn_excluir_usuario!!.setOnClickListener {
            if (networkCheck.isConnected()) {
                progressBar!!.isVisible = true
                presenter.excluirUsuario(id_usuario.toString(), this)
            }
            else
            {
                displayErrorMessage("Verifique sua conexão.")
            }
        }

        btn_logout!!.setOnClickListener {
            presenter.Logout(this)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            true
        } else super.onKeyDown(keyCode, event)
    }

    private val networkCheck by lazy {
        NetworkCheck(ContextCompat.getSystemService(this, ConnectivityManager::class.java)!!)
    }

    override fun displayErrorMessage(mensagem: String) {
        progressBar!!.isVisible = false
        if(mensagem != "")
        {
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        }
    }

    override fun displaySucessToast(mensagem: String) {
        progressBar!!.isVisible = false
        if(mensagem != ""){
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    override fun bindViews() {
        val intent = intent
        id_usuario = intent.getStringExtra("idUsuario").toString()
        email_usuario = intent.getStringExtra("emailUsuario").toString()
        senha_usuario = intent.getStringExtra("senhaUsuario").toString()

        btn_listar_contatos = findViewById<View>(R.id.btnListarContatos) as Button
        btn_editar_usuario = findViewById<View>(R.id.btnEditarUsuario) as Button
        btn_excluir_usuario = findViewById<View>(R.id.btnExcluirUsuário) as Button
        btn_logout = findViewById<View>(R.id.btnLogout) as Button
        progressBar = findViewById<ProgressBar>(R.id.progressBarId) as ProgressBar
    }
}


