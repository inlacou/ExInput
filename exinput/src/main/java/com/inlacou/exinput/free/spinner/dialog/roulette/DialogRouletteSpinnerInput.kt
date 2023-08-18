package com.inlacou.exinput.free.spinner.dialog.roulette

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker
import com.inlacou.exinput.free.spinner.dialog.SingleDialogSpinnerInput

/**
 * Created by inlacou on 14/06/17.
 */
open class DialogRouletteSpinnerInput : SingleDialogSpinnerInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	private var currentTemporalSelection = 0

	override fun closeInput() {
		dialog?.dismiss()
		dialog = null
	}

	override fun openInput() {
		dialog = AlertDialog.Builder(context).apply {
			setPositiveButton(acceptButtonText) { dialog, which ->
				onSelected()
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
				setOnValueChangedListener { picker, oldVal, newVal ->
					currentTemporalSelection = newVal
				}
				currentTemporalSelection = currentSelectionPosition ?: 0
				this.value = currentSelectionPosition ?: 0
			})
			setOnCancelListener { onNothingSelected() }
		}.show()
	}

	private fun onSelected() {
		if (currentTemporalSelection == currentSelectionPosition) onNothingSelected()
		else onItemSelected(currentTemporalSelection)
		dialog?.dismiss()
	}

}