package com.inlacou.exinput.free.spinner.dialog.list

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import com.inlacou.exinput.free.spinner.dialog.SingleDialogSpinnerInput

/**
 * Created by inlacou on 14/06/17.
 */
open class DialogListSpinnerInput : SingleDialogSpinnerInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	private var currentTemporalSelection = -1
	var directSelection = false

	override fun closeInput() {
		dialog?.dismiss()
		dialog = null
	}

	override fun openInput() {
		val builder: AlertDialog.Builder = AlertDialog.Builder(context)
		//builder.setIcon(R.drawable.ic_launcher)
		//builder.setTitle("Select One Name:")
		/*setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }*/
		if(!directSelection) builder.setPositiveButton(acceptButtonText) { dialog, which ->
			onSelected()
		}
		builder.setSingleChoiceItems(adapter, currentSelectionPosition ?: -1) { dialog, which ->
			currentTemporalSelection = which
			if(directSelection) onSelected()
		}
		builder.setOnCancelListener { onNothingSelected() }
		dialog = builder.show()
	}

	private fun onSelected() {
		if(currentTemporalSelection==currentSelectionPosition) onNothingSelected()
		else onItemSelected(currentTemporalSelection)
		dialog?.dismiss()
	}

}