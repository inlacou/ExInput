package com.inlacou.exinput.free.text.email

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import com.inlacou.exinput.utils.extensions.checkEmailCorrect
import com.inlacou.exinput.free.text.TextInput

/**
 * Created by inlacou on 03/10/18.
 */
open class EmailInput : TextInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	init {
		inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
	}

	override fun onTextChanged(s: Editable?) {
		super.onTextChanged(s)
		checkEmailCorrect(false)
	}

	override fun isValid(): Boolean {
		var result = super.isValid()
		result = result && checkEmailCorrect()
		return result
	}
}