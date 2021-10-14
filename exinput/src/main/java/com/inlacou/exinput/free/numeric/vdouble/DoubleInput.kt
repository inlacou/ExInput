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
		@Deprecated("Use float(set), double(set), setFloat, or setDouble instead, as text(set) and setText are ambiguous when decimal and thousand separators can be redefined")
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
	var double: Double?
		get() = text.toDoubleOrNull()
		set(value) {
			if(value==null) setText("")
			else {
				setText(value.toString().replace(".", decimalSeparator).formatDecimal(
					maxDecimals = maxDecimals,
					decimalSeparator = decimalSeparator,
					thousandsSeparator = thousandSeparator,
					markThousands = markThousands))
			}
		}
	var float: Float?
		get() = text.toFloatOrNull()
		set(value) {
			if(value==null) setText("")
			else {
				setText(value.toString().replace(".", decimalSeparator).formatDecimal(
					maxDecimals = maxDecimals,
					decimalSeparator = decimalSeparator,
					thousandsSeparator = thousandSeparator,
					markThousands = markThousands))
			}
		}
	fun setText(double: Double?) {
		if(double==null) setText("")
		else {
			setText(double.toString().replace(".", decimalSeparator).formatDecimal(
				maxDecimals = maxDecimals,
				decimalSeparator = decimalSeparator,
				thousandsSeparator = thousandSeparator,
				markThousands = markThousands))
		}
	}
	fun setText(float: Float?) {
		if(float==null) setText("")
		else {
			setText(float.toString().replace(".", decimalSeparator).formatDecimal(
				maxDecimals = maxDecimals,
				decimalSeparator = decimalSeparator,
				thousandsSeparator = thousandSeparator,
				markThousands = markThousands))
		}
	}

	init {
		keyListener = DigitsKeyListener.getInstance("-0123456789,.")
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
		val minusIndex = newText.indexOf("-")
		if(minusIndex>0) newText = newText.removeRange(minusIndex,minusIndex+1)
		handleMaxDigits()
		newText = newText.formatDecimal(
				maxDecimals = maxDecimals,
				decimalSeparator = decimalSeparator,
				thousandsSeparator = thousandSeparator,
				markThousands = markThousands,
				onDecimalsLimited = {
					snackbar(context.getString(R.string.Max_variable_decimals, maxDecimals.toString()))
				})
		super.onTextChanged(s)
	}

	private fun handleMaxDigits(){
		maxIntegers?.let {
			if(newText.intergerNum(decimalSeparator)>it){
				newText = previousText
				snackbar(context.getString(R.string.expinput_Max_variable_integers, it.toString()))
			}
		}
	}

}