package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import com.udacity.asteroidradar.database.Repository
import com.udacity.asteroidradar.dependencyinjection.Injection
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
        CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val di = Injection()
        val repository = di.repository
        return try {
            repository.updateAsteroids()
            Result.success()
        } catch (e : HttpException) {
            Result.retry()
        }
    }
}