package com.example.crudaves

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.crudaves.databinding.ActivityCrudBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.DELETE

class CrudAveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrudBinding
    private lateinit var btnVoltar: Button
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button
    private  lateinit var ave: Ave
    private lateinit var myProgressBar: ProgressBar


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val objetoRecebido:Ave? = intent.getSerializableExtra("aveSerializable",Ave::class.java)
        binding.tvId.text = objetoRecebido?.id.toString()
        binding.tvNomeCientifico.setText(objetoRecebido?.nomeCientifico)
        binding.tvNome.setText(objetoRecebido?.nome)
        binding.tvApelido.setText(objetoRecebido?.apelido)
        binding.tvLink.setText(objetoRecebido?.link)

        btnVoltar = binding.btnVoltar
        btnDelete = binding.btnDelete
        btnUpdate = binding.btnUpdate

        myProgressBar = binding.myProgressBar
        myProgressBar.isIndeterminate = true
        myProgressBar.visibility = View.INVISIBLE

        //Conexão com o servidor NodeJs na porta 3000
        val retrofitconfig = RetrofitConfig.getRetrofit()
        //Usar a conexão com o NodeJs através dos métodos da interface Service
        val service = retrofitconfig.create(Service::class.java)


        btnVoltar.setOnClickListener{
            val intent = Intent(this@CrudAveActivity,MainActivity::class.java)
            finish()
            startActivity(intent)
        }

        btnUpdate.setOnClickListener{
            ave = Ave(
                binding.tvId.text.toString().toInt(),
                binding.tvNomeCientifico.text.toString(),
                binding.tvNome.text.toString(),
                binding.tvApelido.text.toString(),
                binding.tvLink.text.toString()
            )
            val atualizar = service.alterarAve(binding.tvId.text.toString().toInt(), ave)

            atualizar!!.enqueue(object : Callback<Comunicado?>{
                override fun onResponse(call: Call<Comunicado?>, response: Response<Comunicado?>) {
                    if(response.isSuccessful){
                        myProgressBar.visibility = View.GONE
                        Toast.makeText(getBaseContext(), "Ave atualizada com sucesso!", Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(this@CrudAveActivity,"ERRO",Toast.LENGTH_LONG).show()
                        val errorMessage = response.errorBody().toString()
                        Toast.makeText(this@CrudAveActivity, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Comunicado?>, t: Throwable) {
                    myProgressBar.visibility = View.GONE
                    val messageProblem: String = t.message.toString()
                    Toast.makeText(this@CrudAveActivity,messageProblem,Toast.LENGTH_LONG).show()
                }
            })
        }

        btnDelete.setOnClickListener{
            val delete = service.excluirAve(binding.tvId.text.toString().toInt())
            delete!!.enqueue(object  : Callback<Comunicado?>{
                override fun onResponse(call: Call<Comunicado?>, response: Response<Comunicado?>) {
                    if(response.isSuccessful){
                        myProgressBar.visibility = View.GONE
                        Toast.makeText(getBaseContext(), "Ave deletada com sucesso!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@CrudAveActivity,MainActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this@CrudAveActivity,"ERRO",Toast.LENGTH_LONG).show()
                        val errorMessage = response.errorBody().toString()
                        Toast.makeText(this@CrudAveActivity, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Comunicado?>, t: Throwable) {
                    myProgressBar.visibility = View.GONE
                    val messageProblem: String = t.message.toString()
                    Toast.makeText(this@CrudAveActivity,messageProblem,Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}