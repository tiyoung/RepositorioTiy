package com.mv.listadecontatos.network

import com.google.gson.annotations.SerializedName
import com.mv.listadecontatos.models.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class NetworkUtils {
    companion object {

        var BASE_URL: String = "https://inovacao-teste.herokuapp.com/api/"

        fun getRetrofitInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    interface EndpointUser {
        @GET("user/email/{email}/password/{senha}")
        fun getDadosLogin(@Path("email") email: String, @Path("senha") senha: String) : Call<List<Usuario>>

        @POST("user")
        fun postNovoUsuario(@Body userData: UsuarioNovo): Call<Usuario>

        @PUT("user")
        fun putEditarUsuario(@Body userData: UsuarioEditar): Call<List<Usuario>>

        @HTTP(method = "DELETE", path = "user", hasBody = true)
        fun deleteUsuario(@Body userData: UsuarioDeletar): Call<Usuario>
    }

    interface EndPointContacts{
        @GET("contact-list/person-email/{user_email}")
        fun getListarContatos(@Path("user_email") user_email: String) : Call<ArrayList<Contato>>

        @POST("contact-list")
        fun postNovoContato(@Body userData: ContatoNovo): Call<Contato>

        @PUT("contact-list")
        fun putEditarContato(@Body userData: ContatoEditar): Call<ArrayList<Contato>>

        @HTTP(method = "DELETE", path = "contact-list", hasBody = true)
        fun deleteContato(@Body userData: ContatoExcluir): Call<Contato>
    }
}