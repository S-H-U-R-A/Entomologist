package com.pragma.entomologistapp.core.ext

fun Int.formatTwoDigits(): String {
    return String.format("%02d", this)
}
