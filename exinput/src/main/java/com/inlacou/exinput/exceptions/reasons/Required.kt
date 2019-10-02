package com.inlacou.exinput.exceptions.reasons

class Required: Reason {
    override fun toString(): String {
        return "${javaClass.name}: Required field"
    }
}