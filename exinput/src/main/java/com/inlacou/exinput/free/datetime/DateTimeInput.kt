package com.inlacou.exinput.free.datetime

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Handler
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import com.inlacou.exinput.BaseInput
import com.inlacou.exinput.ExInputConfig
import com.inlacou.exinput.R
import com.inlacou.exinput.utils.extensions.*
import java.util.*

/**
 * Created by inlacou on 14/06/17.
 */
open class DateTimeInput : BaseInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	var mode24h = true
	var value: Calendar? = null
		set(newVal) {
			newVal.let { value ->
				if(value!=null && value.timeInMillis<=maxDate?.timeInMillis ?: Long.MAX_VALUE && value.timeInMillis>=minDate?.timeInMillis ?: Long.MIN_VALUE)
					field = value
				else if(value==null) field = value
			}
			update()
		}
	var minDate: Calendar? = null
		set(newMin) {
			field = newMin
			value?.let { newMin?.let { newMin -> if(newMin.timeInMillis>it.timeInMillis){
				value = null
				update()
			} } }
		}
	var maxDate: Calendar? = null
		set(newMax) {
			field = newMax
			value?.let { newMax?.let { newMax -> if(it.timeInMillis>newMax.timeInMillis){
				value = null
				update()
			} } }
		}

	init {
		keyListener = DigitsKeyListener.getInstance("")
		isLongClickable = false
	}

	override fun readAttrs(attrs: AttributeSet) {
		super.readAttrs(attrs)
		val ta = context.obtainStyledAttributes(attrs, R.styleable.DateTimeInput, 0, 0)
		try {
			if (ta.hasValue(R.styleable.DateTimeInput_mode24h)) mode24h = ta.getBoolean(R.styleable.DateTimeInput_mode24h, mode24h)
		} finally {
			ta.recycle()
		}
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		setOnClickListener {
			work()
		}
		setOnFocusChangeListener { view, focused ->
			if(focused) work()
		}
	}

	@SuppressLint("CheckResult")
	protected fun hideKeyboard(){
		Handler().postDelayed( { context.hideKeyboard(this) } , 100)
	}

	protected fun showDateDialog(onSet: (() -> Unit)? = null){
		val now = Calendar.getInstance()
		val dialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
			val newValue = (value ?: Calendar.getInstance())
			newValue.set(Calendar.SECOND, 0)
			newValue.set(Calendar.MILLISECOND, 0)

			newValue.set(Calendar.YEAR, year)
			newValue.set(Calendar.MONTH, month)
			newValue.set(Calendar.DAY_OF_MONTH, dayOfMonth)
			value = newValue
			onSet?.invoke()
		}, (value?:now).get(Calendar.YEAR), (value?:now).get(Calendar.MONTH), (value?:now).get(Calendar.DAY_OF_MONTH))

		dialog.setOnCancelListener { value = null }

		minDate?.let { dialog.datePicker.minDate = it.timeInMillis }
		maxDate?.let { dialog.datePicker.maxDate = it.timeInMillis }

		dialog.show()
	}

	protected fun showTimeDialog(onSet: (() -> Unit)? = null){
		val now = Calendar.getInstance()
		val dialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hour, minute ->
			val newValue = (value ?: Calendar.getInstance())
			newValue.set(Calendar.SECOND, 0)
			newValue.set(Calendar.MILLISECOND, 0)

			newValue.set(Calendar.HOUR_OF_DAY, hour)
			newValue.set(Calendar.MINUTE, minute)
			value = newValue
			onSet?.invoke()
		}, (value?:now).get(Calendar.HOUR_OF_DAY), (value?:now).get(Calendar.MINUTE), mode24h)

		dialog.setOnCancelListener { value = null }

		dialog.show()
	}

	protected open fun work(){
		hideKeyboard()
		showDateDialog {
			showTimeDialog()
		}
	}

	protected open fun update(){
		value.let {
			if(it!=null)    setText(ExInputConfig.toDateTime.invoke(it))
			else    setText("")
		}
	}

}