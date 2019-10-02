package com.inlacou.exinput.free.text.email

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import com.inlacou.exinput.exceptions.reasons.AboveMaxLength
import com.inlacou.exinput.exceptions.reasons.BelowMinLength
import com.inlacou.exinput.exceptions.reasons.InvalidEmailAddress
import com.inlacou.exinput.exceptions.reasons.Reason
import com.inlacou.exinput.utils.extensions.checkEmailCorrect
import com.inlacou.exinput.free.text.TextInput
import com.inlacou.exinput.utils.extensions.checkMinLength

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

	override fun getInvalidReasons(): List<Reason> {
		val reasons = mutableListOf<Reason>()
		reasons.addAll(super.getInvalidReasons())
		if(!checkEmailCorrect(false)) {
			reasons.add(InvalidEmailAddress(text))
		}
		return reasons
	}

	override fun isValid(): Boolean {
		var result = super.isValid()
		result = result && checkEmailCorrect()
		return result
	}
}