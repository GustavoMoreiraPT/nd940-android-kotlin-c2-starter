package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.udacity.asteroidradar.database.Asteroid as Entity

class MainViewModel(val database: AsteroidDatabaseDao, application: Application) : AndroidViewModel(application) {

    // FIXME: Change this back to database.getAll() once API is connected
    var asteroids = seedAsteroids()

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid?>()

    val navigateToAsteroidDetail : LiveData<Asteroid?>
        get() = _navigateToAsteroidDetail

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidNavigated(){
        _navigateToAsteroidDetail.value = null
    }

    fun seedAsteroids() : MutableLiveData<List<Entity>>{

        val seededAsteroids = mutableListOf<Entity>()
        seededAsteroids.add((Entity(codename = "Test 1")))
        seededAsteroids.add((Entity(codename = "Test 2")))

        val liveDataSeeded : MutableLiveData<List<Entity>> = MutableLiveData()
        liveDataSeeded.value = seededAsteroids
        return liveDataSeeded
    }

    private suspend fun insert(asteroid: Entity) {
        withContext(Dispatchers.IO) {
            database.insert(asteroid)
        }
    }
}
