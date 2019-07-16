package com.maas.soft.i_eye.model

data class Point (
        val type : Type,
        val x : Double,
        val y : Double,
        val description : String,   //뺄거같음
        val fid : String,
        val tid : String,
        val busNumber : String,
        val routeId : String,

        val turnType : String,
        val distance : Int,
        val facilityName : String
)