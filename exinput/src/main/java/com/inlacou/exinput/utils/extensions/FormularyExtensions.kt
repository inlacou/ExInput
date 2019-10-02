package com.inlacou.exinput.utils.extensions

import android.widget.TextView
import com.inlacou.exinput.BaseInput
import com.inlacou.exinput.R
import com.inlacou.exinput.free.text.TextInput

/**
 * Returns false if incorrect (empty), true if correct (not empty)
 */
fun TextView?.checkNotEmpty(errorResId: Int = R.string.expinput_Required_field): Boolean {
	if(this!=null && (text==null || text.isEmpty())){
		error = context.getString(errorResId)
		requestFocus()
		return false
	}
	return true
}

/**
 * Returns false if incorrect (email invalid), true if not correct (email valid)
 */
fun TextView?.checkEmailCorrect(requestFocus: Boolean = true): Boolean {
	if(this!=null && !text.toString().isValidEmail()){
		error = context.getString(R.string.expinput_Email_not_valid)
		if(requestFocus) requestFocus()
		return false
	}
	return true
}

/**
 * Returns false if incorrect (phone invalid), true if not correct (phone valid)
 */
fun TextView?.checkPhoneCorrect(requestFocus: Boolean = true): Boolean {
	if(this!=null && !text.toString().isValidPhone()){
		error = context.getString(R.string.expinput_Phone_not_valid)
		if(requestFocus) requestFocus()
		return false
	}
	return true
}

fun TextView?.checkSame(other: TextView?, stringResId: Int = R.string.expinput_Passwords_must_match): Boolean {
	if(this!=null && other!=null && this.text.toString()!=other.text.toString()){
		error = context.getString(stringResId)
		requestFocus()
		return false
	}
	return true
}

/**
 * Return true if valid, false if not
 * Return true if below max, false if above or equal
 */
fun TextInput?.checkMaxLength(max: Int, requestFocus: Boolean = true): Boolean {
	return checkMaxLength(max.toLong(), requestFocus)
}

/**
 * Return true if valid, false if not
 * Return true if below max, false if above or equal
 */
fun TextView?.checkMaxLength(max: Long, requestFocus: Boolean = true): Boolean {
	if(this!=null && this.text.toString().length>max){
		//error = "${this.text.toString().length}/$max"
		error = context.getString(R.string.expinput_Field_length_too_long)
		if(requestFocus) requestFocus()
		return false
	}
	return true
}

fun TextView?.checkMinLength(min: Int, requestFocus: Boolean = true): Boolean {
	return checkMinLength(min.toLong(), requestFocus)
}

/**
 * Return true if valid, false if not
 * Return true if below max, false if below or equal
 */
fun TextView?.checkMinLength(min: Long, requestFocus: Boolean = true): Boolean {
	if(this!=null && this.text.toString().length<min){
		error = "${this.text.toString().length}/$min"
		if(requestFocus) requestFocus()
		return false
	}
	return true
}

/**
 * Return true if valid, false if not
 */
fun BaseInput?.checkExactLength(length: Int): Boolean {
	if(this!=null && this.text.length!=length){
		error = context.getString(R.string.expinput_Field_length_variable, length)
		requestFocus()
		return false
	}
	return true
}

fun BaseInput?.checkExactLengths(lengths: List<Int>): Boolean {
	if(lengths.size==1){
		return checkExactLength(lengths[0])
	}else if(lengths.isEmpty()){
		return true
	}
	if(this!=null && lengths.find { this.text.length==it }==null){
		var lengthsString = ""
		lengths.take(lengths.size-2).forEach { lengthsString += "$it, " }
		lengthsString += lengths[lengths.size-2]
		error = context.getString(R.string.expinput_Field_length_one_of_variable_or_variable, lengthsString, lengths.last())
		requestFocus()
		return false
	}
	return true
}

fun BaseInput?.checkOnlyNumbers(): Boolean {
	if(this!=null && !this.text.isNumeric()){
		error = context.getString(R.string.expinput_Field_can_only_contain_numbers)
		requestFocus()
		return false
	}
	return true
}