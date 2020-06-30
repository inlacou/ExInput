package com.inlacou.exinput.free.spinner.dialog

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import com.inlacou.exinput.free.spinner.SpinnerInput

/**
 * Created by inlacou on 14/06/17.
 */
open class DialogSpinnerInput : SpinnerInput {
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
		builderSingle.setTitle("Select One Name:-")

		val arrayAdapter = ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice)
		arrayAdapter.add("Hardik")
		arrayAdapter.add("Archit")
		arrayAdapter.add("Jignesh")
		arrayAdapter.add("Umang")
		arrayAdapter.add("Gatti")

		builderSingle.setNegativeButton("cancel") { dialog, which ->
			dialog.dismiss()
		}

		builderSingle.setAdapter(arrayAdapter) { dialog, which ->
			text = arrayAdapter.getItem(which) ?: ""
		}
		dialog = builderSingle.show()
	}

}