package com.mv.listadecontatos.activity

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.mv.listadecontatos.R
import com.mv.listadecontatos.mvp.contract.EditarContatoContract
import com.mv.listadecontatos.mvp.presenter.EditarContatoPresenter
import com.mv.listadecontatos.utils.Mask
import com.mv.listadecontatos.utils.NetworkCheck

class EditarContatoActivity : AppCompatActivity(), EditarContatoContract.View {

    override lateinit var presenter: EditarContatoPresenter

    //Campos UI
    private var txt_nome_contato: EditText? = null
    private var txt_email_contato: EditText? = null
    private var txt_celular_contato: EditText? = null
    private var btn_editar_contato: Button? = null
    private var btn_excluir_contato: Button? = null
    var progressBar: ProgressBar? = null

    //Variaveis
    private var id_contato: String? = null
    private var nome_contato: String? = null
    private var email_contato: String? = null
    private var celular_contato: String? = null
    private var email_usuario: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_contato)

        presenter = EditarContatoPresenter(this)
        presenter.start()

        txt_celular_contato!!.addTextChangedListener(Mask.insert("(##)####-####",
            txt_celular_contato!!
        ));

        btn_editar_contato!!.setOnClickListener {
            nome_contato = txt_nome_contato?.text.toString()
            email_contato = txt_email_contato?.text.toString()
            celular_contato = txt_celular_contato?.text.toString()

            nome_contato = txt_nome_contato?.text.toString()
            email_contato = txt_email_contato?.text.toString()
            celular_contato = txt_celular_contato?.text.toString()

            HideKeyBoard()
            if (networkCheck.isConnected()) {
                progressBar!!.isVisible = true
                presenter.editarContato(
                    email_contato.toString(),
                    nome_contato.toString(),
                    email_usuario.toString(),
                    celular_contato.toString(),
                    id_contato.toString()
                )
            }
            else{
                displayErrorMessage("Verifique sua conexão.")
            }
        }
        btn_excluir_contato!!.setOnClickListener {
            HideKeyBoard()
            if (networkCheck.isConnected()) {
                progressBar!!.isVisible = true
                presenter.excluirContato(id_contato.toString(), this)
            }else
            {
                displayErrorMessage("Verifique sua conexão.")
            }
        }
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
        id_contato = intent.getStringExtra("idContato")
        nome_contato = intent.getStringExtra("nomeContato")
        email_contato = intent.getStringExtra("emailContato")
        celular_contato = intent.getStringExtra("celularContato")
        email_usuario = intent.getStringExtra("emailUsuario")

        txt_nome_contato = findViewById<View>(R.id.txtNomeContatoEdt) as EditText
        txt_email_contato = findViewById<View>(R.id.txtEmailContatoEdt) as EditText
        txt_celular_contato = findViewById<View>(R.id.txtCelularContatoEdt) as EditText
        btn_editar_contato = findViewById<View>(R.id.btnEditarContato) as Button
        btn_excluir_contato = findViewById<View>(R.id.btnExcluirContato) as Button
        progressBar = findViewById<ProgressBar>(R.id.progressBarId) as ProgressBar

        txt_nome_contato!!.setText(nome_contato.toString())
        txt_email_contato!!.setText(email_contato.toString())
        txt_celular_contato!!.setText(celular_contato.toString())
    }

    fun HideKeyBoard(){
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}