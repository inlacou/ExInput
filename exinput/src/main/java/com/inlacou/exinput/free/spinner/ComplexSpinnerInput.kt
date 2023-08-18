package com.inlacou.exinput.free.spinner

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import com.inlacou.exinput.free.FreeInput

/**
 * Created by inlacou on 30/06/20.
 */
abstract class ComplexSpinnerInput<Item> : FreeInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	abstract var currentSelection: Item?
	private var currentTemporalSelection = currentSelection

	/**
	 * Actual text value
	 */
	override var text: String
		set(value) { setText(value) }
		get() { return getText().toString() }

	var onItemSelectedListener: OnItemSelectedListener<Item>? = null
	var onItemSelectedCallback: ((ComplexSpinnerInput<Item>, Item?) -> Unit)? = null
	var onNothingSelectedCallback: ((ComplexSpinnerInput<Item>) -> Unit)? = null
	var itemToString: ((Item) -> String)? = null

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

	protected fun onItemSelected(item: Item?) {
		currentSelection = item
		text = item?.let { itemToString?.invoke(it) } ?: item?.toString() ?: ""
		onItemSelectedCallback?.invoke(this, item)
		onItemSelectedListener?.onItemSelected(this, item)
	}

	protected fun onNothingSelected() {
		onNothingSelectedCallback?.invoke(this)
		onItemSelectedListener?.onNothingSelected(this)
	}

	abstract fun openInput()
	abstract fun closeInput()

	interface OnItemSelectedListener<Item> {
		fun onItemSelected(parent: ComplexSpinnerInput<Item>, item: Item?)
		fun onNothingSelected(parent: ComplexSpinnerInput<Item>)
	}

}