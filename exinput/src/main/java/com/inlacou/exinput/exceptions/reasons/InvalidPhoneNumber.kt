package com.inlacou.exinput.exceptions.reasons

class InvalidPhoneNumber(val phoneNumber: String): Reason {
    override fun toString(): String {
        return "${javaClass.name}: Invalid phone number: \"$phoneNumber\""
    }
}