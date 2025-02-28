package com.snehadatta.taroo.util

interface JsonParser {

    fun <T> fromJson(json: String,type: java.lang.reflect.Type) :T?
    fun <T> toJson (obj: T , type: java.lang.reflect.Type) : String?
}