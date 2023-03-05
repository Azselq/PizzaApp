package com.example.pizzaapp.utils

/**
 * Проверяет зарежку между текущим временем и тем что было до этого
 */
fun Long.checkDelayIsOff(delay: Long = 500): Boolean {
    if (this < 0) return true
    return System.currentTimeMillis() - delay > this
}