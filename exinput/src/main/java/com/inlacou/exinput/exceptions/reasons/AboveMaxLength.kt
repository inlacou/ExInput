package com.inlacou.exinput.exceptions.reasons

class AboveMaxLength(private val text: String, val maxLength: Int): Reason {
    override fun toString(): String {
        return "${javaClass.name}: Content \"$text\" above max length: $maxLength"
    }
}