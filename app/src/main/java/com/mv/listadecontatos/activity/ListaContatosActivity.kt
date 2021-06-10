package com.mv.listadecontatos.activity

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.mv.listadecontatos.R
import com.mv.listadecontatos.models.Contato
import com.mv.listadecontatos.adapter.ListaAdapterService
import com.mv.listadecontatos.mvp.*
import com.mv.listadecontatos.mvp.contract.ListaContatoContract
import com.mv.listadecontatos.mvp.presenter.ListaContatoPresenter
import com.mv.listadecontatos.utils.NetworkCheck

class ListaContatosActivity : AppCompatActivity(), ListaContatoContract.View {
    override lateinit var presenter: ListaContatoPresenter

    private var btn_novo_contato: Button? = null
    var lista: ListView? = null
    var email_usuario: String = ""
    var contatos = ArrayList<Contato>()
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_contatos)

        presenter = ListaContatoPresenter(this)
        presenter.start()

        /*var contatos = ArrayList<Contato>()
        contatos.add(Contato("0","Ti Young", "teste@gmail.com","81988376621", 0))
        contatos.add(Contato("0","Ti Young222", "teste222@gmail.com","51941894948", 0))

        var adapter : ListaAdapter = ListaAdapter(this,contatos)
        lista!!.adapter = adapter*/

        if (networkCheck.isConnected()) {
            progressBar!!.isVisible = true
            presenter.listarContatos(email_usuario, this)
        }
        else
        {
            displayErrorMessage("Verifique sua conexão")
        }

        btn_novo_contato!!.setOnClickListener {
            val intent = Intent(this, NovoContatoActivity::class.java)
            intent.putExtra("emailUsuario", email_usuario)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        if (networkCheck.isConnected()) {
            progressBar!!.isVisible = true
            presenter.listarContatos(email_usuario, this)
        }
        else
        {
            displayErrorMessage("Verifique sua conexão")
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

    override fun startActivityNew() {

    }

    override fun mostrarContatos(contatos: ArrayList<Contato>) {
        var adapter: ListaAdapterService = ListaAdapterService(this@ListaContatosActivity, contatos)
        lista!!.adapter = adapter

        progressBar!!.isVisible = false

        lista!!.setOnItemClickListener { parent, view, position, id ->
            var id_contato: String = contatos.get(position).id
            var nome_contato: String = contatos.get(position).nome
            /*No serviço, no endpoint "contact-list/person-email/{user_email}" não está vindo o campo e-mail do contato. O e-mail que vem la é do usuário ao qual o contato está vinculado*/
            var email_contato: String = ""
            var celular_contato: String = contatos.get(position).celular

            val intent = Intent(applicationContext, EditarContatoActivity::class.java)
            intent.putExtra("idContato", id_contato)
            intent.putExtra("nomeContato", nome_contato)
            intent.putExtra("emailContato", email_contato)
            intent.putExtra("celularContato", celular_contato)
            intent.putExtra("emailUsuario", email_usuario)
            startActivity(intent)
        }
    }

    override fun bindViews() {
        val intent = intent
        email_usuario = intent.getStringExtra("emailUsuario").toString()

        lista = findViewById<View>(R.id.lista) as ListView
        btn_novo_contato = findViewById<View>(R.id.btnNovoContato) as Button
        progressBar = findViewById<ProgressBar>(R.id.progressBarId) as ProgressBar
    }
}