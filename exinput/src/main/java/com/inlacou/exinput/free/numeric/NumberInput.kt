package com.inlacou.exinput.free.numeric

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import com.inlacou.exinput.R
import com.inlacou.exinput.utils.extensions.digitsNum
import com.inlacou.exinput.utils.extensions.snackbar
import com.inlacou.exinput.free.FreeInput
import timber.log.Timber

/**
 * Created by inlacou on 14/06/17.
 */
abstract class NumberInput : FreeInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	companion object {
		const val DEFAULT_DECIMAL_SEPARATOR = ","
		const val DEFAULT_THOUSAND_SEPARATOR = "."
	}

	var maxDigits: Int? = null
	var markThousands: Boolean = false
	var thousandSeparator = DEFAULT_THOUSAND_SEPARATOR
	var decimalSeparator = DEFAULT_DECIMAL_SEPARATOR

	override var text: String
		set(value) { setText(value) }
		get() { return getText().toString() }

	override fun onTextChanged(s: Editable?){
		handleMaxDigits()
		super.onTextChanged(s)
	}

	override fun readAttrs(attrs: AttributeSet) {
		super.readAttrs(attrs)
		val ta = context.obtainStyledAttributes(attrs, R.styleable.NumberInput, 0, 0)
		try {
			if (ta.hasValue(R.styleable.NumberInput_maxDigits)) {
				maxDigits = ta.getInteger(R.styleable.NumberInput_maxDigits, -1)
				maxDigits?.let { if(it<0) maxDigits = null }
			}
			if (ta.hasValue(R.styleable.NumberInput_markThousands)) markThousands = ta.getBoolean(R.styleable.NumberInput_markThousands, false)
			if (ta.hasValue(R.styleable.NumberInput_thousandSeparator)) thousandSeparator = ta.getString(R.styleable.NumberInput_thousandSeparator) ?: DEFAULT_THOUSAND_SEPARATOR
			if (ta.hasValue(R.styleable.NumberInput_decimalSeparator)) decimalSeparator = ta.getString(R.styleable.NumberInput_decimalSeparator) ?: DEFAULT_DECIMAL_SEPARATOR
		} finally {
			ta.recycle()
		}
	}

	private fun handleMaxDigits(){
		maxDigits?.let {
			if(newText.digitsNum>it){
				Timber.d("afterTextChanged | maxDigits: $newText to $previousText")
				newText = previousText
				snackbar(context.getString(R.string.expinput_Max_variable_digits, maxDigits.toString()))
			}
		}
	}

}