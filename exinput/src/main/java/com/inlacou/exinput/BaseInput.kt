package com.inlacou.exinput

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewParent
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.inlacou.exinput.exceptions.InvalidException
import com.inlacou.exinput.exceptions.reasons.Reason
import com.inlacou.exinput.exceptions.reasons.Required
import com.inlacou.exinput.utils.extensions.checkNotEmpty

/**
 * Created by inlacou on 14/06/17.
 */
abstract class BaseInput : TextInputEditText {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	var required = false

	companion object {
		const val DRAWABLE_LEFT = 0
		const val DRAWABLE_TOP = 1
		const val DRAWABLE_RIGHT = 2
		const val DRAWABLE_BOTTOM = 3
	}

	/**
	 * Actual text value
	 */
	open var text: String
		set(value) { setText(value) }
		get() { return getText().toString() }

	protected open fun readAttrs(attrs: AttributeSet) {
		val ta = context.obtainStyledAttributes(attrs, R.styleable.BaseInput, 0, 0)
		try {
			if (ta.hasValue(R.styleable.BaseInput_required)) {
				required = ta.getBoolean(R.styleable.BaseInput_required, required)
			}
		} finally {
			ta.recycle()
		}
	}

	/**
	 * Function to determine if contents are valid, depending on configuration
	 */
	open fun isValid(): Boolean {
		var result = true
		if(required) result = result && checkNotEmpty()
		return result
	}

	/**
	 * Function to determine if contents are valid, depending on configuration
	 * @throws InvalidException detailing why it failed
	 */
	open fun isValidThrowExceptions(): Boolean {
		getInvalidReasons().let{
			if(it.isNotEmpty()){
				throw InvalidException(it)
			}
		}
		return true
	}

	protected open fun getInvalidReasons(): List<Reason> {
		val reasons = mutableListOf<Reason>()
		if(required && !checkNotEmpty()) {
			reasons.add(Required())
		}
		return reasons
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		enableError()
	}

	override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
		setSelection(text.length)
		super.onFocusChanged(focused, direction, previouslyFocusedRect)
	}

	override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter)
		error = null
	}

	private fun enableError(){
		try {
			(parent.parent as TextInputLayout).isErrorEnabled = true
		}catch (e: NullPointerException){}catch (e: IllegalStateException){}catch (e: UnsupportedOperationException){}catch (e: ClassCastException){}
	}

	override fun setError(error: CharSequence?) {
		parent?.let { propagateError(it, error) }
	}

	private fun propagateError(view: ViewParent, error: CharSequence?){
		if(view is TextInputLayout) {
			view.error = error
		}
		view.parent?.let { propagateError(it, error) }
	}
}