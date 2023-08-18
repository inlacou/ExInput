package com.inlacou.exinput.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.TextView
import com.inlacou.exinput.free.datetime.DateInput
import com.inlacou.exinput.rx.CheckBoxObs
import com.inlacou.exinput.rx.OnClickObs
import com.inlacou.exinput.rx.input.DateTimeInputObs
import com.inlacou.exinput.rx.input.TextChangeObs
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

//https://es.wikipedia.org/wiki/Separador_de_millares
fun String.formatDecimal(maxDecimals: Int, force: Boolean = false, markThousands: Boolean = true,
                         decimalSeparator: String = ",", thousandsSeparator: String = ".",
                         onDecimalsLimited: (() -> Unit)? = null, log: Boolean = false): String {
	val secret = "%&%&lñjkndfkljWEkljkjsdgrkbnldflkjnjzdgKJFELBEUEW"
	if(log) Timber.d("formatDecimal -------------------------")
	if(log) Timber.d("formatDecimal: $this | decimalSeparator: $decimalSeparator")
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
	if(log) Timber.d("formatDecimal | curated: $curated")
	if(curated==".") return "0$decimalSeparator"

	val separatorPosition = curated.lastIndexOf(".")
	if(log) Timber.d("formatDecimal | separatorPosition: $separatorPosition")

	try {
		//Integer part
		var integerPart = (curated.substring(0, if (separatorPosition == -1) curated.length else separatorPosition))
		if(log) Timber.d("formatDecimal | integerPart: $integerPart")
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
		if(log) Timber.d("formatDecimal | decimalPart: $decimalPart")

		if(force) {
			if(decimalPart==null){
				decimalPart = ""
			}
			while (decimalPart.length<maxDecimals){
				decimalPart += "0"
			}
		}
		if(log) Timber.d("formatDecimal | decimalPart: $decimalPart")

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

		if(log) Timber.d("formatDecimal | force: '$force'" +
				" | !contains(decimalSeparator): '${!contains(decimalSeparator)}'" +
				" | sign: '$sign'" +
				" | decimalSeparator: '$decimalSeparator'" +
				" | maxDecimals: '$maxDecimals'" +
				" | integerPart: '$integerPart' | decimalPart: '$decimalPart'")
		return when {
			force && decimalPart!="" -> sign + integerPart + decimalSeparator + decimalPart
			force && maxDecimals==0 -> sign + integerPart
			!contains(decimalSeparator) || maxDecimals == 0 -> sign + integerPart
			decimalPart == "" -> sign + integerPart + decimalSeparator
			else -> sign + integerPart + decimalSeparator + decimalPart
		}.also {
			if(log) Timber.d("formatDecimal | returning: $it")
		}
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

/* UI */
fun View.clicks(): Observable<View> {
	return Observable.create(OnClickObs(this))
}

fun TextView.textChanges(): Observable<String> {
	return Observable.create(TextChangeObs(this))
}

fun DateInput.textChanges(): Observable<DateTimeInputObs.Item> {
	return Observable.create(DateTimeInputObs(this))
}

fun CheckBox.checkedChanges(): Observable<Boolean> {
	return Observable.create(CheckBoxObs(this))
}
/* /UI */

