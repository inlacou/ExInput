package com.inlacou.exinput.exceptions.reasons

class BelowMinLength(private val text: String, val minLength: Int): Reason {
    override fun toString(): String {
        return "${javaClass.name}: Content \"$text\" below min length: $minLength"
    }
}