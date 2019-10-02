package com.inlacou.exinput.exceptions.reasons

class InvalidEmailAddress(val emailAddress: String): Reason {
    override fun toString(): String {
        return "${javaClass.name}: Invalid email address: \"$emailAddress\""
    }
}