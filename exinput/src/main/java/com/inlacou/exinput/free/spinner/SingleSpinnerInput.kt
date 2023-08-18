package com.inlacou.exinput.free.spinner

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.ListAdapter
import com.inlacou.exinput.free.FreeInput

/**
 * Created by inlacou on 30/06/20.
 */
abstract class SingleSpinnerInput : FreeInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	/**
	 * Actual text value
	 */
	override var text: String
		set(value) { setText(value) }
		get() { return getText().toString() }

	var currentSelectionPosition: Int? = null
	var adapter: ListAdapter? = null
	var onItemSelectedListener: OnItemSelectedListener? = null
	var onItemSelectedCallback: ((SingleSpinnerInput, Any?, Int) -> Unit)? = null
	var onNothingSelectedCallback: ((SingleSpinnerInput) -> Unit)? = null

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

	protected fun onItemSelected(index: Int) {
		currentSelectionPosition = index
		text = adapter?.getItem(index)?.toString() ?: ""
		onItemSelectedCallback?.invoke(this, adapter?.getItem(index), index)
		onItemSelectedListener?.onItemSelected(this, adapter?.getItem(index), index)
	}

	protected fun onNothingSelected() {
		onNothingSelectedCallback?.invoke(this)
		onItemSelectedListener?.onNothingSelected(this)
	}

	abstract fun openInput()
	abstract fun closeInput()

	interface OnItemSelectedListener {
		fun onItemSelected(parent: SingleSpinnerInput, item: Any?, position: Int)
		fun onNothingSelected(parent: SingleSpinnerInput)
	}

}