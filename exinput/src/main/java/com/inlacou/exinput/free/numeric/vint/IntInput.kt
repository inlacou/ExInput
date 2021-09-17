package com.inlacou.exinput.free.numeric.vint

import android.content.Context
import android.text.Editable
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import com.inlacou.exinput.utils.extensions.formatDecimal
import com.inlacou.exinput.free.numeric.NumberInput
import timber.log.Timber

/**
 * Created by inlacou on 14/06/17.
 */
open class IntInput : NumberInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	override var text: String
		@Deprecated("Use int(set), or setInt instead, as text(set) and setText are ambiguous when decimal and thousand separators can be redefined")
		set(value) {
			setText(value.formatDecimal(
					maxDecimals = 0,
					decimalSeparator = decimalSeparator,
					thousandsSeparator = thousandSeparator,
					markThousands = markThousands))
		}
		get() { return getText().toString() }
	var int: Int?
		get() = text.toIntOrNull()
		set(value) {
			if(value==null) setText("")
			else {
				setText(value.toString().formatDecimal(
						maxDecimals = 0,
						decimalSeparator = ".",
						thousandsSeparator = ",",
						markThousands = markThousands))
			}
		}
	fun setText(int: Int?) {
		if(int==null) setText("")
		else {
			setText(int.toString().formatDecimal(
				maxDecimals = 0,
				decimalSeparator = ".",
				thousandsSeparator = ",",
				markThousands = markThousands))
		}
	}

	init {
		keyListener = DigitsKeyListener.getInstance("0123456789")
	}

	override fun onTextChanged(s: Editable?){
		newText = newText.formatDecimal(
				maxDecimals = 0,
				decimalSeparator = decimalSeparator,
				thousandsSeparator = thousandSeparator,
				markThousands = markThousands)
		Timber.d("afterTextChanged | $newText to $newText")
		super.onTextChanged(s)
	}
}