package com.inlacou.exinput.free.spinner.dialog

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import com.inlacou.exinput.R
import com.inlacou.exinput.free.spinner.ComplexSpinnerInput

/**
 * Created by inlacou on 14/06/17.
 */
abstract class ComplexDialogSpinnerInput<Item> : ComplexSpinnerInput<Item> {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	protected var dialog: AlertDialog? = null
	var acceptButtonText: String = context.getString(R.string.exinput_accept_button_text)
}