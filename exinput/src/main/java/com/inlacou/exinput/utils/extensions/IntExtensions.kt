package com.inlacou.exinput.utils.extensions

import android.content.res.Resources

val Int.digitsNum: Int
	get() = toString().length

fun Long.toStringMinDigits(minDigits: Int): String {
	var result = this.toString()
	while (result.length<minDigits){
		result = "0$result"
	}
	return result
}

fun Int.toStringMinDigits(minDigits: Int): String {
	var result = this.toString()
	while (result.length<minDigits){
		result = "0$result"
	}
	return result
}

fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Int.pxToDp() = (this / Resources.getSystem().displayMetrics.density).toInt()
