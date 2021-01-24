package com.udacity.asteroidradar.dependencyinjection

import android.app.Application
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.Repository

class Injection : Application() {

    val database by lazy { AsteroidDatabase.getInstance(this) }

    val repository by lazy { Repository(database) }
}