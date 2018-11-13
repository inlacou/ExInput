package com.inlacou.exinput.free.text

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import com.inlacou.exinput.R
import com.inlacou.exinput.utils.extensions.checkMaxLength
import com.inlacou.exinput.utils.extensions.checkMinLength
import com.inlacou.exinput.free.FreeInput

/**
 * Created by inlacou on 14/06/17.
 */
open class TextInput : FreeInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	/**
	 * Sets the max character counter below the EditText
	 */
	var maxLength: Int? = null
		set(value) {
			field = value
			updateMaxLength()
		}
	var minLength: Int? = null
		set(value) {
			field = value
			updateMinLength()
		}

	/**
	 * Actual text value
	 */
	override var text: String
		set(value) {
			setText(value)
		}
		get() {
			return getText().toString()
		}

	override fun readAttrs(attrs: AttributeSet) {
		super.readAttrs(attrs)
		val ta = context.obtainStyledAttributes(attrs, R.styleable.TextInput, 0, 0)
		try {
			if (ta.hasValue(R.styleable.TextInput_maxLength)) {
				maxLength = ta.getInteger(R.styleable.TextInput_maxLength, -1)
				maxLength?.let { if(it<0) maxLength = null }
			}
			if (ta.hasValue(R.styleable.TextInput_minLength)) {
				minLength = ta.getInteger(R.styleable.TextInput_minLength, -1)
				minLength?.let { if(it<0) minLength = null }
			}
		} finally {
			ta.recycle()
		}
	}

	override fun onTextChanged(s: Editable?) {
		super.onTextChanged(s)
		updateMinLength()
	}

	private fun updateMinLength() {
		maxLength?.let { checkMaxLength(it, false) }
		minLength?.let { checkMinLength(it, false) }
	}

	/**
	 * Function to determine if contents are valid, depending on configuration
	 */
	override fun isValid(): Boolean {
		var result = super.isValid()
		result = result && (maxLength?.let { checkMaxLength(it) } ?: result)
		result = result && (minLength?.let { checkMinLength(it) } ?: result)
		return result
	}

	private fun updateMaxLength(){
		maxLength.let { maxLength ->
			parent?.let {
				if(it is TextInputLayout) {
					if(maxLength!=null) {
						it.counterMaxLength = maxLength
						it.isCounterEnabled = true
					} else it.isCounterEnabled = false
				}
				it.parent?.let {
					if(it is TextInputLayout) {
						if(maxLength!=null) {
							it.counterMaxLength = maxLength
							it.isCounterEnabled = true
						} else it.isCounterEnabled = false
					}
				}
			}
		}
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		updateMaxLength()
	}
}