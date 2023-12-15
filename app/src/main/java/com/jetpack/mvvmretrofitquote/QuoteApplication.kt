package com.jetpack.mvvmretrofitquote

import android.app.Application
import androidx.constraintlayout.widget.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.jetpack.mvvmretrofitquote.api.QuoteService
import com.jetpack.mvvmretrofitquote.api.RetrofitHelper
import com.jetpack.mvvmretrofitquote.db.QuoteDatabase
import com.jetpack.mvvmretrofitquote.repository.QuoteRespository
import com.jetpack.mvvmretrofitquote.worker.QuoteWorker
import java.util.concurrent.TimeUnit

class QuoteApplication : Application() {

    lateinit var quoteRespository: QuoteRespository

    override fun onCreate() {
        super.onCreate()
        initialize()
        setWorker()
    }

    private fun setWorker() {
        val constraint = androidx.work.Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequest = PeriodicWorkRequest.Builder(QuoteWorker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    private fun initialize() {
        // declare it in common location like activity class or use dependecy injection
        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
        quoteRespository = QuoteRespository(quoteService, database, applicationContext)
    }
}