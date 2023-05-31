package com.example.crudaves

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.crudaves.databinding.ActivityCadastroAveBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroAveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroAveBinding
    private lateinit var btnVoltar: Button
    private lateinit var btnCreate: Button
    private lateinit var ave : Ave
    private lateinit var myProgressBar: ProgressBar

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroAveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnVoltar = binding.btnVoltar
        btnCreate = binding.btnCreate

        myProgressBar = binding.myProgressBar
        myProgressBar.isIndeterminate = true
        myProgressBar.visibility = View.INVISIBLE

        btnVoltar.setOnClickListener {
            val intent = Intent(this@CadastroAveActivity,MainActivity::class.java)
            finish()
            startActivity(intent)
        }

        //Conexão com o servidor NodeJs na porta 3000
        val retrofitconfig = RetrofitConfig.getRetrofit()
        //Usar a conexão com o NodeJs através dos métodos da interface Service
        val service = retrofitconfig.create(Service::class.java)

        btnCreate.setOnClickListener{
            ave = Ave(
                binding.tvId.text.toString().toInt(),
                binding.tvNomeCientifico.text.toString(),
                binding.tvNome.text.toString(),
                binding.tvApelido.text.toString(),
                binding.tvLink.text.toString()
            )

            val create = service.incluirAve(ave)

            create!!.enqueue(object : Callback<Comunicado?> {
                override fun onResponse(call: Call<Comunicado?>, response: Response<Comunicado?>) {
                    if(response.isSuccessful){
                        myProgressBar.visibility = View.GONE
                        Toast.makeText(getBaseContext(), "Ave adicionada com sucesso!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@CadastroAveActivity,MainActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this@CadastroAveActivity,"ERRO",Toast.LENGTH_LONG).show()
                        val errorMessage = response.errorBody().toString()
                        Toast.makeText(this@CadastroAveActivity, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Comunicado?>, t: Throwable) {
                    myProgressBar.visibility = View.GONE
                    val messageProblem: String = t.message.toString()
                    Toast.makeText(this@CadastroAveActivity,messageProblem,Toast.LENGTH_LONG).show()
                }
            })
        }

    }
}