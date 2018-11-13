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
		set(value) {
			setText(value.formatDecimal(
					maxDecimals = 0,
					decimalSeparator = decimalSeparator,
					thousandsSeparator = thousandSeparator,
					markThousands = markThousands)) }
		get() { return getText().toString() }

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