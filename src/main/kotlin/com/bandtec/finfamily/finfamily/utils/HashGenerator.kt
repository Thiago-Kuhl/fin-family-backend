package com.bandtec.finfamily.finfamily.utils

import java.util.*
import kotlin.streams.asSequence

fun groupIdGenerator(): String {
    val source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return Random().ints(5, 0, source.length).asSequence().map(source::get).joinToString("")
}