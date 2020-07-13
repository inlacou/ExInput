package com.inlacou.exinput.free.spinner.dialog.list

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import com.inlacou.exinput.free.spinner.SpinnerInput
import com.inlacou.exinput.free.spinner.dialog.DialogSpinnerInput

/**
 * Created by inlacou on 14/06/17.
 */
open class DialogListSpinnerInput : DialogSpinnerInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	private var currentTemporalSelection = 0

	override fun closeInput() {
		dialog?.dismiss()
		dialog = null
	}

	override fun openInput() {
		val builderSingle: AlertDialog.Builder = AlertDialog.Builder(context)
		//builderSingle.setIcon(R.drawable.ic_launcher)
		//builderSingle.setTitle("Select One Name:")
		/*setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }*/
		builderSingle.setPositiveButton(acceptButtonText) { dialog, which ->
			if(currentTemporalSelection==currentSelectionPosition) onNothingSelected()
			else onItemSelected(currentTemporalSelection)
			dialog.dismiss()
		}
		builderSingle.setSingleChoiceItems(adapter, currentSelectionPosition) { dialog, which -> currentTemporalSelection = which }
		builderSingle.setOnCancelListener { onNothingSelected() }
		dialog = builderSingle.show()
	}

}