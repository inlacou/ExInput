package com.inlacou.exinput.free.numeric.vdouble

import android.content.Context
import android.text.Editable
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import com.inlacou.exinput.R
import com.inlacou.exinput.utils.extensions.digitsNum
import com.inlacou.exinput.utils.extensions.formatDecimal
import com.inlacou.exinput.utils.extensions.snackbar
import com.inlacou.exinput.free.numeric.NumberInput
import com.inlacou.exinput.utils.extensions.intergerNum
import timber.log.Timber

/**
 * Created by inlacou on 14/06/17.
 */
open class DoubleInput : NumberInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	var maxIntegers: Int? = null
	var maxDecimals: Int = 2

	override var text: String
		set(value) {
			setText(value.formatDecimal(
					maxDecimals = maxDecimals,
					decimalSeparator = decimalSeparator,
					thousandsSeparator = thousandSeparator,
					markThousands = markThousands))
		}
		get() {
			return getText().toString().replace("", "").replace(",", "")
		}

	init {
		keyListener = DigitsKeyListener.getInstance("0123456789,.")
	}

	override fun readAttrs(attrs: AttributeSet) {
		super.readAttrs(attrs)
		val ta = context.obtainStyledAttributes(attrs, R.styleable.DoubleInput, 0, 0)
		try {
			if (ta.hasValue(R.styleable.DoubleInput_maxIntegers)) {
				maxIntegers = ta.getInteger(R.styleable.DoubleInput_maxIntegers, -1)
				maxIntegers?.let { if(it<0) maxIntegers = null }
			}
			if (ta.hasValue(R.styleable.DoubleInput_maxDecimals))
				maxDecimals = ta.getInteger(R.styleable.DoubleInput_maxDecimals, 2)
		} finally {
			ta.recycle()
		}
	}

	override fun onTextChanged(s: Editable?){
		handleMaxDigits()
		newText = newText.formatDecimal(
				maxDecimals = maxDecimals,
				decimalSeparator = decimalSeparator,
				thousandsSeparator = thousandSeparator,
				markThousands = markThousands,
				onDecimalsLimited = {
					snackbar(context.getString(R.string.Max_variable_decimals, maxDecimals.toString()))
				})
		Timber.d("afterTextChanged | $newText to $newText")
		super.onTextChanged(s)
	}

	private fun handleMaxDigits(){
		maxIntegers?.let {
			if(newText.intergerNum(decimalSeparator)>it){
				Timber.d("afterTextChanged | maxIntegers: $newText to $previousText")
				newText = previousText
				snackbar(context.getString(R.string.expinput_Max_variable_integers, it.toString()))
			}
		}
	}

}