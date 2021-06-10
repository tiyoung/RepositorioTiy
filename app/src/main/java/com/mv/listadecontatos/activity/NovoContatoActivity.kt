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
import com.mv.listadecontatos.mvp.contract.NovoContatoContract
import com.mv.listadecontatos.mvp.presenter.NovoContatoPresenter
import com.mv.listadecontatos.utils.Mask
import com.mv.listadecontatos.utils.NetworkCheck

class NovoContatoActivity : AppCompatActivity(), NovoContatoContract.View {
    override lateinit var presenter: NovoContatoPresenter

    //Campos UI
    private var txt_nome_contato: EditText? = null
    private var txt_email_contato: EditText? = null
    private var txt_celular_contato: EditText? = null
    private var btn_cadastrar_contato: Button? = null
    var progressBar: ProgressBar? = null

    //Variaveis
    private var nome_contato: String? = null
    private var email_contato: String? = null
    private var celular_contato: String? = null
    private var email_usuario: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_contato)

        presenter = NovoContatoPresenter(this)
        presenter.start()

        txt_celular_contato!!.addTextChangedListener(Mask.insert("(##)####-####",txt_celular_contato!!));

        btn_cadastrar_contato!!.setOnClickListener {
            HideKeyBoard()
            if (networkCheck.isConnected()) {
                progressBar!!.isVisible = true
                nome_contato = txt_nome_contato?.text.toString()
                email_contato = txt_email_contato?.text.toString()
                celular_contato = txt_celular_contato?.text.toString()

                presenter.criarContato(email_contato.toString(), nome_contato.toString(), email_usuario.toString(), celular_contato.toString())
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
        if(mensagem != ""){
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        }
        txt_nome_contato!!.setText("")
        txt_nome_contato!!.requestFocus()
        txt_email_contato!!.setText("")
        txt_celular_contato!!.setText("")
    }

    override fun bindViews() {
        val intent = intent
        email_usuario = intent.getStringExtra("emailUsuario")

        txt_nome_contato = findViewById<View>(R.id.txtNomeContato) as EditText
        txt_email_contato = findViewById<View>(R.id.txtEmailContato) as EditText
        txt_celular_contato = findViewById<View>(R.id.txtCelularContato) as EditText
        btn_cadastrar_contato = findViewById<View>(R.id.btnCadastrarContato) as Button
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