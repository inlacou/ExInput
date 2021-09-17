package com.inlacou.exinput.free

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.inlacou.exinput.BaseInput
import timber.log.Timber

/**
 * Created by inlacou on 14/06/17.
 */
abstract class FreeInput: BaseInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	/**
	 * Just a holder for previous valid text
	 */
	internal var previousText = ""
	var initialText = ""
	var newText = ""

	/**
	 * Actual text value
	 */
	override var text: String
		set(value) { setText(value) }
		get() { return getText().toString() }

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		//Set the listener for text handling
		addTextChangedListener(object : TextWatcher{
			override fun afterTextChanged(s: Editable?) {
				Timber.d("afterTextChanged: current ${s.toString()}")
				initialText = s.toString()
				newText = initialText
				onTextChanged(s)
			}
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
		})
	}

	open fun onTextChanged(s: Editable?) {
		if(newText==initialText) { //If non-transitory, acceptable value
			previousText = initialText //We save it
			Timber.d("afterTextChanged | same | saved previous text: $previousText")
		}
		if(newText!=initialText) { //If different than current
			Timber.d("afterTextChanged | different | write it: $newText")
			super.text = newText //Write it
			setSelection(newText.length) //Move selection to end
		}
	}

}