package com.mv.listadecontatos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mv.listadecontatos.activity.ListaContatosActivity
import com.mv.listadecontatos.R
import com.mv.listadecontatos.models.Contato


class ListaAdapterService(val context: ListaContatosActivity, val contatos: ArrayList<Contato>) : BaseAdapter(){
    override fun getCount(): Int {
        return contatos.size
    }

    override fun getItem(position: Int): Any {
        return contatos.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.linha_layout, null)
        val nome = view.findViewById<TextView>(R.id.textViewNome)
        val celular = view.findViewById<TextView>(R.id.textViewCelular)
        nome.text = contatos.get(position).nome
        celular.text = contatos.get(position).celular
        return view;
    }
}


