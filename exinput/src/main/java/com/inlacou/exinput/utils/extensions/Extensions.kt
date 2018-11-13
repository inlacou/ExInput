package com.inlacou.exinput.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import timber.log.Timber
import java.math.BigDecimal
import java.math.RoundingMode

//https://es.wikipedia.org/wiki/Separador_de_millares
fun String.formatDecimal(maxDecimals: Int, force: Boolean = false, markThousands: Boolean = true,
                         decimalSeparator: String = ",", thousandsSeparator: String = ".",
                         roundingMode: RoundingMode = RoundingMode.FLOOR, onDecimalsLimited: (() -> Unit)? = null): String {
	val secret = "%&%&lñjkndfkljWEkljkjsdgrkbnldflkjnjzdgKJFELBEUEW"
	Timber.d("formatDecimal -------------------------")
	Timber.d("formatDecimal: $this | decimalSeparator: $decimalSeparator")
	if (trim().isEmpty() or trim().equals("nan", ignoreCase = true) or trim().equals("null", ignoreCase = true)) {
		return this
	}

	var curated = this.replaceFirst(decimalSeparator, secret).replace(decimalSeparator, "")

	val sign = if(curated.substring(0,1)=="-"){
		curated = curated.replace("-", "")
		"-"
	}else{
		""
	}

	curated = curated.replace(" ", "")
	curated = curated.replace(".", "")
	curated = curated.replace(",", "")
	curated = curated.replace(secret, ".")
	Timber.d("formatDecimal | curated: $curated")

	var bd = BigDecimal(curated)
	bd = bd.setScale(maxDecimals, roundingMode)
	val value = bd.toString().replace(".", decimalSeparator)
	Timber.d("formatDecimal | value: $curated")

	val separatorPosition = value.lastIndexOf(decimalSeparator)

	try {
		//Integer part
		var integerPart = (value.substring(0, if (separatorPosition == -1) value.length else separatorPosition))
		//Decimal part
		var decimalPart = when {
			separatorPosition == -1 -> null
			separatorPosition + 1 < curated.length -> curated.substring(
					separatorPosition + 1,
					if (separatorPosition + 1 + maxDecimals < curated.length) {
						onDecimalsLimited?.invoke()
						separatorPosition + 1 + maxDecimals
					}
					else curated.length)
			else -> ""
		}

		if(force){
			if(decimalPart==null){
				decimalPart = ""
			}
			while (decimalPart.length<maxDecimals){
				decimalPart += "0"
			}
		}

		val newThousandsSeparator = if(thousandsSeparator!=decimalSeparator){
			thousandsSeparator
		}else{
			when(decimalSeparator){
				"," -> "."
				"." -> ","
				else -> ","
			}
		}

		if(markThousands) {
			var lastDot = integerPart.length
			while (lastDot - 3 > 0) {
				val first = integerPart.substring(0, lastDot - 3)
				val second = integerPart.substring(lastDot - 3, integerPart.length)
				integerPart = "$first$newThousandsSeparator$second"
				lastDot -= 3
			}
		}

		Timber.d("formatDecimal | force: $force" +
				" | !contains(decimalSeparator): ${!contains(decimalSeparator)}" +
				" | maxDecimals: $maxDecimals" +
				" | integerPart: $integerPart | decimalPart: $decimalPart")
		return when {
			force && decimalPart!="" -> sign + integerPart + decimalSeparator + decimalPart
			force && maxDecimals==0 -> sign + integerPart
			!contains(decimalSeparator) || maxDecimals == 0 -> sign + integerPart
			decimalPart == "" -> sign + integerPart + decimalSeparator
			else -> sign + integerPart + decimalSeparator + decimalPart
		}
		/*
		return if (force) value
		else if (!contains(decimalSeparator) || maxDecimals == 0) integerPart.toString()
		else if (decimalPart == "") integerPart.toString() + decimalSeparator
		else integerPart.toString() + decimalSeparator + decimalPart
		*/
	}catch (nfe: NumberFormatException){
		return ""
	}
}

fun Context.getColorCompat(resId: Int): Int {
	return resources.getColorCompat(resId)
}

fun Context.getDrawableCompat(resId: Int): Drawable {
	return resources.getDrawableCompat(resId)
}

fun Resources.getDrawableCompat(resId: Int): Drawable {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		getDrawable(resId, null)
	}else{
		getDrawable(resId)
	}
}

fun Resources.getColorCompat(resId: Int): Int {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		getColor(resId, null)
	}else{
		getColor(resId)
	}
}

fun Context.hideKeyboard(view: View){
	val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
	imm.hideSoftInputFromWindow(view.windowToken, 0)
}

