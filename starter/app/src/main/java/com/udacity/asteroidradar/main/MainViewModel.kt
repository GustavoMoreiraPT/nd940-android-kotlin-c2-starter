package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.Repository
import com.udacity.asteroidradar.enums.AsteroidFilter
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(val database: Repository, application: Application) : AndroidViewModel(application) {

    private val _filter = MutableLiveData(AsteroidFilter.NONE)

    val asteroids = Transformations.switchMap(_filter){
        when(it) {
            AsteroidFilter.DAILY -> database.todayAsteroids
            AsteroidFilter.WEEKLY -> database.weeklyAsteroid
            else -> database.allAsteroids
        }
    }

    private val _imageOfDay = MutableLiveData<PictureOfDay>()
    val imageOfDay: LiveData<PictureOfDay>
        get() = _imageOfDay

    init {
        displayAsteroids()
        displayPictureOfDay()
    }

    private fun displayAsteroids() {
        viewModelScope.launch {
            database.updateAsteroids()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayPictureOfDay() {
        viewModelScope.launch {
            database.updatePictureOfDay()
            _imageOfDay.value = database.getPictureOfDay()
        }
    }

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid?>()

    val navigateToAsteroidDetail: LiveData<Asteroid?>
        get() = _navigateToAsteroidDetail

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidNavigated() {
        _navigateToAsteroidDetail.value = null
    }

    fun setFilter(filter: AsteroidFilter) {
        _filter.postValue(filter)
    }
}
