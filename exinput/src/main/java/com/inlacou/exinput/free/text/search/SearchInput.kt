package com.inlacou.exinput.free.text.search

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import com.inlacou.exinput.R
import com.inlacou.exinput.utils.extensions.getDrawableCompat
import com.inlacou.exinput.utils.extensions.setDrawableLeft
import com.inlacou.exinput.utils.extensions.setDrawableRight
import com.inlacou.exinput.utils.extensions.setVisible
import com.inlacou.exinput.utils.listeners.OnTextViewDrawableTouchListener
import com.inlacou.exinput.free.text.TextInput

/**
 * Created by inlacou on 13/11/18.
 */
open class SearchInput : TextInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	var hideOnEmptyClear = true
	var holdSpaceOnHide = false

	/**
	 * Returns true if event is consumed
	 */
	var onClearClickListener: (() -> Boolean) = { false }

	init {
		inputType = InputType.TYPE_CLASS_TEXT
		setOnTouchListener(object : OnTextViewDrawableTouchListener(){
			override fun onDrawableClick(touchTarget: TouchTarget) {
				if(touchTarget==TouchTarget.RIGHT) onClearClick()
			}
		})
		setDrawableLeft(padding = compoundDrawablePadding, drawable = context.getDrawableCompat(R.drawable.search_grey))
		setDrawableRight(padding = compoundDrawablePadding, drawable = context.getDrawableCompat(R.drawable.cancel))
	}

	fun callOnClearClick(){
		onClearClick()
	}

	private fun onClearClick(){
		if(onClearClickListener.invoke()) return
		else {
			if(text.isNotEmpty()) text = ""
			else if(hideOnEmptyClear) setVisible(visible = false, holdSpaceOnDissapear = holdSpaceOnHide)
		}
	}

}