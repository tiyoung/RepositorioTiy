package com.mv.listadecontatos.mvp.presenter

import android.app.AlertDialog
import android.content.Context
import androidx.core.view.isVisible
import com.mv.listadecontatos.models.Usuario
import com.mv.listadecontatos.models.UsuarioDeletar
import com.mv.listadecontatos.mvp.contract.MenuContract
import com.mv.listadecontatos.network.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuPresenter(private val view : MenuContract.View) : MenuContract.Presenter {

    override fun excluirUsuario(id_usuario: String, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmar")
        builder.setMessage("Deseja excluir esse usuário?")

        builder.setPositiveButton(
            "SIM"
        ) { dialog, which -> // Do nothing but close the dialog
            dialog.dismiss()

            val retrofitClient = NetworkUtils.getRetrofitInstance()
            val endpoint = retrofitClient.create(NetworkUtils.EndpointUser::class.java)

            val usuarioInfo = UsuarioDeletar(
                id = id_usuario!!
            )

            val callback = endpoint.deleteUsuario(usuarioInfo)

            callback.enqueue(object : Callback<Usuario>{
                override fun onResponse(
                    call: Call<Usuario>,
                    response: Response<Usuario>
                ) {
                    if(response.isSuccessful){
                        //Toast.makeText(baseContext, "Usuário excluído com sucesso!", Toast.LENGTH_SHORT).show()
                        view.displaySucessToast("Usuário excluído com sucesso!")
                        //finish()
                    }
                    else{

                        var code: Int = response.code()
                        println("**********"+response.code())
                        println("**********"+response.message())

                        if(code == 500){
                            //Toast.makeText(baseContext, "Erro ao excluir usuário.", Toast.LENGTH_SHORT).show()
                            view.displayErrorMessage("Erro ao excluir usuário.")
                        }
                        else{
                            //Sem permissão
                        }
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    //Toast.makeText(baseContext, "Erro ao excluir usuário."+t.message, Toast.LENGTH_SHORT).show()
                    view.displayErrorMessage("Erro ao excluir usuário.")
                }
            })
        }

        builder.setNegativeButton(
            "NÃO"
        ) { dialog, which -> // Do nothing
            dialog.dismiss()
            view.displayErrorMessage("")
        }

        val alert = builder.create()
        alert.show()
    }

    override fun Logout(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmar")
        builder.setMessage("Deseja sair?")

        builder.setPositiveButton(
            "SIM"
        ) { dialog, which -> // Do nothing but close the dialog
            dialog.dismiss()
            view.displaySucessToast("")
        }

        builder.setNegativeButton(
            "NÃO"
        ) { dialog, which -> // Do nothing
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    override fun start() {
        view.bindViews()
    }
}