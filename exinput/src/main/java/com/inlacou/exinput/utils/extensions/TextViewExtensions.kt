package com.inlacou.exinput.utils.extensions

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.TextView

fun TextView.setTextColorResId(colorResId: Int){
	this.setTextColor(ColorStateList.valueOf(this.context.getColorCompat(colorResId)))
}

fun EditText.setSelectionEnd(){
	setSelection(text.length)
}

/**
 * If not resId given, mantain the previous
 */
fun TextView.setDrawable(padding: Int, leftResId: Int? = null, topResId: Int? = null, rightResId: Int? = null, botResId: Int? = null){
	setCompoundDrawablesWithIntrinsicBounds(when{
		leftResId==null -> compoundDrawables[0]
		leftResId>=0 -> context.getDrawableCompat(leftResId)
		else -> null
	}, when{
		topResId==null -> compoundDrawables[1]
		topResId>=0 -> context.getDrawableCompat(topResId)
		else -> null
	}, when{
		rightResId==null -> compoundDrawables[2]
		rightResId>=0 -> context.getDrawableCompat(rightResId)
		else -> null
	}, when{
		botResId==null -> compoundDrawables[3]
		botResId>=0 -> context.getDrawableCompat(botResId)
		else -> null
	})
	compoundDrawablePadding = padding
}

fun TextView.setDrawableLeft(drawable: Drawable?, padding: Int? = null){
	setCompoundDrawablesWithIntrinsicBounds(drawable, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3])
	padding?.let { compoundDrawablePadding = padding }
}

fun TextView.setDrawableRight(drawable: Drawable?, padding: Int? = null){
	setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], compoundDrawables[1], drawable, compoundDrawables[3])
	padding?.let { compoundDrawablePadding = padding }
}