package com.inlacou.exinput.free.text.password

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import com.inlacou.exinput.R
import com.inlacou.exinput.utils.extensions.getDrawableCompat
import com.inlacou.exinput.utils.extensions.setDrawableRight
import com.inlacou.exinput.utils.extensions.setSelectionEnd
import com.inlacou.exinput.utils.listeners.OnTextViewDrawableTouchListener
import com.inlacou.exinput.free.text.TextInput
import com.inlacou.exinput.free.text.password.PasswordInput.Mode.*

/**
 * Created by inlacou on 03/10/18.
 */
open class PasswordInput : TextInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	var mode: Mode = PASSWORD
		set(value) {
			val change = field != value
			field = value
			if(change) {
				update()
			}
		}

	/**
	 * Whether to show the showHideButton or not
	 */
	var showHideButton = true
		set(value) {
			val change = field != value
			field = value
			if(change) {
				if (!value) {
					setDrawableRight(initialRightDrawable)
				}
				update()
			}
		}

	private val initialRightDrawable: Drawable? = compoundDrawables[DRAWABLE_RIGHT]

	init {
		setOnTouchListener(object : OnTextViewDrawableTouchListener(){
			override fun onDrawableClick(touchTarget: TouchTarget) {
				if(touchTarget==TouchTarget.RIGHT && showHideButton) changeMode()
			}
		})
		update()
	}

	override fun readAttrs(attrs: AttributeSet) {
		super.readAttrs(attrs)
		val ta = context.obtainStyledAttributes(attrs, R.styleable.PasswordInput, 0, 0)
		try {
			if (ta.hasValue(R.styleable.PasswordInput_showHideButton)) showHideButton = ta.getBoolean(R.styleable.PasswordInput_showHideButton, showHideButton)
		} finally {
			ta.recycle()
		}
	}

	private fun setNormalMode(){
		val cache = typeface
		inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
		typeface = cache
		if(showHideButton) setDrawableRight(padding = compoundDrawablePadding, drawable = context.getDrawableCompat(R.drawable.hide))
		setSelectionEnd()
	}

	private fun setPasswordMode(){
		val cache = typeface
		inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
		typeface = cache
		if(showHideButton) setDrawableRight(padding = compoundDrawablePadding, drawable = context.getDrawableCompat(R.drawable.show))
		setSelectionEnd()
	}

	private fun update(){
		when(mode){
			NORMAL -> setNormalMode()
			PASSWORD -> setPasswordMode()
		}
	}

	fun changeMode(){
		mode = when(mode){
			NORMAL -> PASSWORD
			PASSWORD -> NORMAL
		}
	}

	enum class Mode {
		NORMAL, PASSWORD
	}

}