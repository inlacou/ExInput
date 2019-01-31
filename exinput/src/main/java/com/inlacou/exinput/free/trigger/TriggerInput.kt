package com.inlacou.exinput.free.trigger

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
import com.inlacou.exinput.R.attr.mode24h
import com.inlacou.exinput.utils.extensions.*
import java.util.*

/**
 * Created by inlacou on 14/06/17.
 */
open class TriggerInput : BaseInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

    var onWork: (() -> Unit)? = null

    var value: Any? = null

	init {
		keyListener = DigitsKeyListener.getInstance("")
		isLongClickable = false
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

	protected open fun work(){
		hideKeyboard()
        onWork?.invoke()
	}

}