package com.example.crudaves

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.crudaves.databinding.ActivityCadastroAveBinding
import com.squareup.picasso.Picasso
import java.io.Serializable

class GridViewAdapter(
        private val aveList: List<Ave>,
        private val context: Context
    ) :
    BaseAdapter() {

        private lateinit var aveImageView: ImageView
        private lateinit var tvNomeCientifico: TextView
        private lateinit var tvNome: TextView
        private lateinit var tvApelido: TextView


        override fun getCount(): Int {
            return aveList.size
        }

        override fun getItem(posicao: Int): Ave {
            return aveList.get(posicao)
        }

        override fun getItemId(posicao: Int): Long {
            return posicao.toLong()
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View? {
            var view = view
            if (view == null) {
                view =
                    LayoutInflater.from(context).inflate(R.layout.layout_gridview, viewGroup, false)
            }

            aveImageView = view!!.findViewById(R.id.aveImageView)
            tvNomeCientifico = view!!.findViewById(R.id.tvNomeCientifico)
            tvNome = view!!.findViewById(R.id.tvNome)
            tvApelido = view!!.findViewById(R.id.tvApelido)

            val avePosicao: Ave = aveList.get(position)
            tvNomeCientifico.text = avePosicao.nomeCientifico
            tvNome.text = avePosicao.nome
            tvApelido.text = avePosicao.apelido

            if(avePosicao.link != null && avePosicao.link.length > 0){
                Picasso.with(context).load(avePosicao.link).into(aveImageView)
            }
            else {
                Toast.makeText(context,"Não carregou a imagem", Toast.LENGTH_LONG).show()
            }

            view.setOnClickListener {
                val aveObjeto: Ave = Ave(avePosicao.id,avePosicao.nomeCientifico,avePosicao.nome,avePosicao.apelido,avePosicao.link)
                val intent: Intent = Intent(context,CrudAveActivity::class.java)
                intent.putExtra("aveSerializable", aveObjeto as Serializable?)
                context.startActivity(intent)
            }

            return view
        }
}
