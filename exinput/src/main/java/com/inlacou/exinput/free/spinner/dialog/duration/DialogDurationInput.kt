package com.inlacou.exinput.free.spinner.dialog.duration

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import com.inlacou.exinput.R
import com.inlacou.exinput.free.spinner.dialog.ComplexDialogSpinnerInput

/**
 * Created by inlacou on 14/06/17.
 */
open class DialogDurationInput : ComplexDialogSpinnerInput<DialogDurationInput.Duration> {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	data class Duration(val hours: Int? = null, val minutes: Int? = null, val seconds: Int? = null)

	var showMinutes: Boolean = true
	var showSeconds: Boolean = true

	override var currentSelection: Duration? = Duration(null, if(showMinutes) 0 else null, if(showSeconds) 0 else null)
	private var currentTemporalSelection = currentSelection?.copy()

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
			setView(LinearLayout(context).apply {
				setVerticalGravity(Gravity.CENTER_VERTICAL)
				setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
				// Minutes spinner
				addView(NumberPicker(context).apply {
					displayedValues = Array(60) { it.toString() }
					minValue = 0
					maxValue = 59
					setOnValueChangedListener { picker, oldVal, newVal ->
						currentTemporalSelection = currentSelection?.copy(minutes = newVal)
					}
					currentTemporalSelection = currentSelection?.copy(minutes = currentSelection!!.minutes)
					this.value = currentTemporalSelection?.minutes ?: 0
				})
				addView(TextView(context).apply {
					setText(R.string.minutes)
					setVerticalGravity(Gravity.CENTER_VERTICAL)
				})
				// Second spinner
				addView(NumberPicker(context).apply {
					displayedValues = Array(60) { it.toString() }
					minValue = 0
					maxValue = 59
					setOnValueChangedListener { picker, oldVal, newVal ->
						currentTemporalSelection = currentSelection?.copy(seconds = newVal)
					}
					currentTemporalSelection = currentSelection?.copy(seconds = currentSelection!!.seconds)
					this.value = currentTemporalSelection?.seconds ?: 0
				})
				addView(TextView(context).apply {
					setText(R.string.seconds)
					setVerticalGravity(Gravity.CENTER_VERTICAL)
				})
			})
			setOnCancelListener { onNothingSelected() }
		}.show()
	}

	private fun onSelected() {
		if (currentTemporalSelection == currentSelection) onNothingSelected()
		else onItemSelected(currentTemporalSelection)
		dialog?.dismiss()
	}

}