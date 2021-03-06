package com.inlacou.exinput.free.datetime

import android.content.Context
import android.util.AttributeSet
import com.inlacou.exinput.ExInputConfig

/**
 * Created by inlacou on 14/06/17.
 */
open class TimeInput : DateTimeInput {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) { readAttrs(attrSet) }
	constructor(context: Context, attrSet: AttributeSet, arg: Int) : super(context, attrSet, arg) { readAttrs(attrSet) }

	override fun work(){
		hideKeyboard()
		showTimeDialog()
	}

	override fun update(){
		value.let {
			if(it!=null)    setText(ExInputConfig.toTime.invoke(it))
			else    setText("")
		}
	}

}