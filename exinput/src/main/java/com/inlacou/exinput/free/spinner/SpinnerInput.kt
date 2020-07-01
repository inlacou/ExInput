package com.inlacou.exinput.free.spinner

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import com.inlacou.exinput.free.FreeInput

/**
 * Created by inlacou on 14/06/17.
 */
abstract class SpinnerInput : FreeInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	/**
	 * Actual text value
	 */
	override var text: String
		set(value) { setText(value) }
		get() { return getText().toString() }

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		tag = keyListener
		keyListener = null
		setOnClickListener {
			openInput()
		}
	}

	override fun onDetachedFromWindow() {
		super.onDetachedFromWindow()
		closeInput()
	}

	override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect)
		if(focused) { openInput() }
	}

	abstract fun openInput()
	abstract fun closeInput()

}