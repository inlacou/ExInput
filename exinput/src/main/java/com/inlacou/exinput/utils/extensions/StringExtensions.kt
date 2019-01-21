package com.inlacou.exinput.utils.extensions

import java.lang.Exception
import java.text.DecimalFormat
import java.text.NumberFormat

val String.digitsNum: Int
	get() = this.filter { it.toString().isNumeric() }.length

fun String.intergerNum(decimalSeparator: String): Int = try{
	this.substring(0, this.indexOf(decimalSeparator)).digitsNum
}catch (e: Exception){
	digitsNum
}


fun String?.isNumeric(): Boolean {
	return this?.matches("^[0-9]*$".toRegex()) ?: false
}

fun String?.isValidEmail(): Boolean {
	return this!=null && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.isValidPhone(): Boolean {
	return this!=null && android.util.Patterns.PHONE.matcher(this).matches()
}

val decimalSeparator: String
	get() {
		val nf = NumberFormat.getInstance()
		return if (nf is DecimalFormat) {
			nf.decimalFormatSymbols.decimalSeparator.toString()
		}else {
			"."
		}
	}