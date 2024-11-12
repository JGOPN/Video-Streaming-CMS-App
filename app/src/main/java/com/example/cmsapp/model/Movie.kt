package com.example.cmsapp.model

import java.util.Date

data class Movie(
    val movieId : Int,
    val title : String,
    val description : String,
    val releaseDate : Date,
    val submittedBy : Int,
    val duration : Int,
    val lastWatched : Pair<Date,Int>,
    val genres : List<String>
    )