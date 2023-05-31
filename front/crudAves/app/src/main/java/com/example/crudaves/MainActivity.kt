package com.example.crudaves

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.crudaves.databinding.ActivityCadastroAveBinding
import com.example.crudaves.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myProgressBar:ProgressBar
    private lateinit var actButton:FloatingActionButton
    private lateinit var aveGridView:GridView
    private lateinit var adapter : GridViewAdapter

    private fun populateGridView(body: List<Ave>?) {
        aveGridView = binding.aveGridView
        adapter = GridViewAdapter(body!!,this)
        aveGridView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myProgressBar = binding.myProgressBar
        myProgressBar.isIndeterminate = true
        myProgressBar.visibility = View.VISIBLE

        actButton = binding.actButton
        actButton.setOnClickListener {
            val intent = Intent(this@MainActivity,CadastroAveActivity::class.java)
            finish()
            startActivity(intent)
        }

        //Conexão com o servidor NodeJs na porta 3000
        val retrofitconfig = RetrofitConfig.getRetrofit()
        //Usar a conexão com o NodeJs através dos métodos da interface Service
        val service = retrofitconfig.create(Service::class.java)
        //Usando o método getDog da interface Service
        val callback = service.getAve()


        callback.enqueue(object : Callback<List<Ave>> {
            override fun onResponse(call: Call<List<Ave>>, response: Response<List<Ave>>) {
                if(response.isSuccessful){
                    myProgressBar.visibility = View.GONE
                    populateGridView(response.body()!!)
                }
                else {
                    val errorMessage = response.errorBody().toString()
                    Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Ave>>, t: Throwable) {
                myProgressBar.visibility = View.GONE
                val messageProblem: String = t.message.toString()
                Toast.makeText(this@MainActivity,messageProblem,Toast.LENGTH_LONG).show()
            }

        })


    }


}