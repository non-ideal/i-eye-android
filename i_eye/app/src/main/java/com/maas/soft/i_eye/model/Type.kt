package com.maas.soft.i_eye.model

enum class Type(private val typeMessage : String, private val typeValue : Int) {
    POINT("PathResDto", 0),
    LINE("LineString", 1);
}