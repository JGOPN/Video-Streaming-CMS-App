package com.example.cmsapp.model


data class User(
    val id : Int,
    val name : String,
    val password : String,
    val email : String,
    val isAdmin : Boolean
)