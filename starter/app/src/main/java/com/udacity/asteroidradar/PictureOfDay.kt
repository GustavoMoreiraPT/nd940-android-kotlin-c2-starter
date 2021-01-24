package com.udacity.asteroidradar

import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.PictureOfDay as Entity

data class PictureOfDay(@Json(name = "media_type") val mediaType: String, val title: String,
                        val url: String){

    fun ToEntity() : Entity =
            Entity(
                    title = this.title,
                    url = this.url,
                    mediaType = this.mediaType
            )
}