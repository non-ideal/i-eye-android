package com.maas.soft.i_eye.model

data class PathResDto (
        val busNumber : String,
        val fid : String,
        val routeId : String,
        val tid : String,
        val turnType : String,
        val type : Type,
        val x : Double,
        val y : Double
)