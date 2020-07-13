package com.inlacou.exinput.free.spinner.dialog.list

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import com.inlacou.exinput.free.spinner.SpinnerInput

/**
 * Created by inlacou on 14/06/17.
 */
open class DialogListSpinnerInput : SpinnerInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	private var dialog: AlertDialog? = null

	override fun closeInput() {
		dialog?.dismiss()
		dialog = null
	}

	override fun openInput() {
		val builderSingle: AlertDialog.Builder = AlertDialog.Builder(context)
		//builderSingle.setIcon(R.drawable.ic_launcher)
		//builderSingle.setTitle("Select One Name:")
		builderSingle.setAdapter(adapter) { dialog, which ->
			onItemSelected(which)
		}
		builderSingle.setOnCancelListener {
			onNothingSelected()
		}
		dialog = builderSingle.show()
	}

}