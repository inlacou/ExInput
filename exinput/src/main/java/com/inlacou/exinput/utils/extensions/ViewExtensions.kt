package com.inlacou.exinput.utils.extensions

import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar

fun View?.snackbar(messageResId: Int, length: Int = Snackbar.LENGTH_LONG){
	this?.let { this.snackbar(this.context.getString(messageResId), length) }
}
fun View?.snackbar(message: String, length: Int = Snackbar.LENGTH_LONG){
	this?.let { Snackbar.make(it, message, length).show() }
}

fun View.resizeView(width: Int, height: Int) {
	val lp = layoutParams
	if (width > -1) {
		lp.width = width
	}
	if (height > -1) {
		lp.height = height
	}
	layoutParams = lp
	requestLayout()
}

fun View?.setVisible(visible: Boolean, holdSpaceOnDissapear: Boolean = false) {
	if (this == null) return
	if(visible){
		this.visibility = View.VISIBLE
	}else{
		if(holdSpaceOnDissapear){
			this.visibility = View.INVISIBLE
		}else{
			this.visibility = View.GONE
		}
	}
}

fun View.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
	if (layoutParams is ViewGroup.MarginLayoutParams) {
		val p = layoutParams as ViewGroup.MarginLayoutParams
		p.setMargins(left, top, right, bottom)
		requestLayout()
	}
}

fun View.setPaddings(left: Int, top: Int, right: Int, bottom: Int) {
	setPadding(left, top, right, bottom)
	requestLayout()
}