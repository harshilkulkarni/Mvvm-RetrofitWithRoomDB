package com.jetpack.mvvmretrofitquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jetpack.mvvmretrofitquote.api.QuoteService
import com.jetpack.mvvmretrofitquote.api.RetrofitHelper
import com.jetpack.mvvmretrofitquote.repository.QuoteRespository
import com.jetpack.mvvmretrofitquote.viewmodels.MainViewModel
import com.jetpack.mvvmretrofitquote.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.tv)

        val respository = (application as QuoteApplication).quoteRespository

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(respository, 2))[MainViewModel::class.java]

        mainViewModel.quotes.observe(this) {
            Log.e("=====> ", it.results.toString())
            runOnUiThread(Runnable {
                for (i in 0 until it.results.size) {
                    tv.append("$i) Author: "+it.results[i].author+", Content: "+ it.results[i].content+"\n\n")
                    Log.e("==>$i)", "Author: "+it.results[i].author+", Content: "+ it.results[i].content+"\n")
                }
            })
        }
    }
}