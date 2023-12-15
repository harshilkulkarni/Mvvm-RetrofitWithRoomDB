package com.jetpack.mvvmretrofitquote.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jetpack.mvvmretrofitquote.api.QuoteService
import com.jetpack.mvvmretrofitquote.db.QuoteDatabase
import com.jetpack.mvvmretrofitquote.models.QuoteList
import com.jetpack.mvvmretrofitquote.utils.NetworkUtils

class QuoteRespository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    private val quotesLiveData = MutableLiveData<QuoteList>()

    val quotes: LiveData<QuoteList>
    get() = quotesLiveData

    suspend fun getQuotes(page: Int) {

        if(NetworkUtils.isNetworkAvailable(applicationContext)){
            val result = quoteService.getQuotes(page)
            if (result?.body() != null) {
                // add data from API
                quoteDatabase.quoteDao().addQuotes(result.body()!!.results)

                quotesLiveData.postValue(result.body())
            }
        }else{
            val quotes = quoteDatabase.quoteDao().getQuotes()
            val quoteList = QuoteList(1,1,1,quotes,1,1);
            quotesLiveData.postValue(quoteList)
        }
    }

    // Work Manager
    suspend fun getQuotesBackground(){
        val randomNumber = (Math.random() * 10).toInt()
        Log.e("===> Random No1", randomNumber.toString())
        val result = quoteService.getQuotes(randomNumber)
        if(result?.body() != null){
            Log.e("===> Random No2", "Size: "+result.body()!!.results.size)
            quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
        }

    }
}