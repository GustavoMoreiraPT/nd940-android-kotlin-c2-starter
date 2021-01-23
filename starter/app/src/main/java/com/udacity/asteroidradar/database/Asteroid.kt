package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid as ParcelableAsteroid

@Entity(tableName = "asteroid_info_table")
data class Asteroid(

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,

        @ColumnInfo(name = "codename")
        var codename: String = "",

        @ColumnInfo(name = "close_approach_date")
        var closeApproachDate: String = "",

        @ColumnInfo(name = "absolute_magnitude")
        var absoluteMagnitude: Double = 0.0,

        @ColumnInfo(name = "estimatedDiameter")
        var estimatedDiameter: Double = 0.0,

        @ColumnInfo(name = "relative_velocity")
        var relativeVelocity: Double = 0.0,

        @ColumnInfo(name = "distance_from_earth")
        var distanceFromEarth: Double = 0.0,

        @ColumnInfo(name = "is_potentially_hazardous")
        val isPotentiallyHazardous: Boolean = false
)

fun List<ParcelableAsteroid>.toEntity(): List<Asteroid> {
        return map {
                Asteroid(
                        id = it.id,
                        codename = it.codename,
                        closeApproachDate = it.closeApproachDate,
                        absoluteMagnitude = it.absoluteMagnitude,
                        estimatedDiameter = it.estimatedDiameter,
                        relativeVelocity = it.relativeVelocity,
                        distanceFromEarth = it.distanceFromEarth,
                        isPotentiallyHazardous = it.isPotentiallyHazardous
                )
        }
}

fun List<Asteroid>.toParcelable(): List<ParcelableAsteroid> {
        return map {
                ParcelableAsteroid(
                        id = it.id,
                        codename = it.codename,
                        closeApproachDate = it.closeApproachDate,
                        absoluteMagnitude = it.absoluteMagnitude,
                        estimatedDiameter = it.estimatedDiameter,
                        relativeVelocity = it.relativeVelocity,
                        distanceFromEarth = it.distanceFromEarth,
                        isPotentiallyHazardous = it.isPotentiallyHazardous
                )
        }
}