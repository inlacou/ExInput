package com.inlacou.exinput.free.spinner.dialog.numberpicker

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker
import com.inlacou.exinput.free.spinner.SpinnerInput

/**
 * Created by inlacou on 14/06/17.
 */
open class DialogNumberPickerSpinnerInput : SpinnerInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	private var dialog: AlertDialog? = null
	private var currentTemporalSelection = 0

	override fun closeInput() {
		dialog?.dismiss()
		dialog = null
	}

	override fun openInput() {
		dialog = AlertDialog.Builder(context).apply {
			setPositiveButton("Accept") { dialog, which ->
				if(currentTemporalSelection==currentSelectionPosition) onNothingSelected()
				else onItemSelected(currentTemporalSelection)
				dialog.dismiss()
			}
			/*setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }*/
			setView(NumberPicker(context).apply {
				adapter?.let {
					val strings = Array(it.count) { "" }
					(0 until it.count).forEach { pos -> strings[pos] = it.getItem(pos).toString() }
					displayedValues = strings
					minValue = 0
					maxValue = it.count-1
				}
				setOnValueChangedListener { picker, oldVal, newVal -> currentTemporalSelection = newVal }
				currentTemporalSelection = currentSelectionPosition
				this.value = currentSelectionPosition
			})
			setOnCancelListener { onNothingSelected() }
		}.show()
	}

}