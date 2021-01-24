package com.udacity.asteroidradar.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.NasaGatewayImpl
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate
import com.udacity.asteroidradar.PictureOfDay as PresentableImage

class Repository(private val database: AsteroidDatabase) {


    @RequiresApi(Build.VERSION_CODES.O)
    private val startDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    private val endDate = startDate.plusDays(7)

    val allAsteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDatabaseDao.getAll()) { it.toParcelable() }

    @RequiresApi(Build.VERSION_CODES.O)
    val weeklyAsteroid: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDatabaseDao.getWeeklyAsteroids(startDate.toString(), endDate.toString())) {
                it.toParcelable()
            }

    @RequiresApi(Build.VERSION_CODES.O)
    val todayAsteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDatabaseDao.getTodayAsteroids(startDate.toString()))
            {
                it.toParcelable()
            }

    suspend fun updateAsteroids() {
        withContext(Dispatchers.IO) {
            val response = NasaGatewayImpl.retrofitService.getAsteroids()
            val parsedAsteroids = parseAsteroidsJsonResult(JSONObject(response))
            val dbAsteroids = parsedAsteroids.toEntity()

            dbAsteroids.forEach{ database.asteroidDatabaseDao.insert(it) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updatePictureOfDay() {
        withContext(Dispatchers.IO) {
            val dayPicture = NasaGatewayImpl.retrofitService.getImageOfTheDay(startDate)
            database.asteroidDatabaseDao.insert(dayPicture.ToEntity())
        }
    }

     @WorkerThread
     suspend fun getPictureOfDay(): PresentableImage =
         withContext(Dispatchers.IO){
             database.asteroidDatabaseDao.getPicture().toPresentationImage()
         }
}