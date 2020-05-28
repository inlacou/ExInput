package com.inlacou.exinput.rx

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.inlacou.exinput.free.datetime.DateTimeInput
import com.inlacou.exinput.free.numeric.vdouble.DoubleInput
import com.inlacou.exinput.free.numeric.vint.IntInput
import com.inlacou.exinput.rx.input.DateTimeInputObs
import com.inlacou.exinput.rx.input.DoubleChangeObs
import com.inlacou.exinput.rx.input.LongChangeObs
import com.inlacou.exinput.rx.input.TextChangeObs
import com.inlacou.exinput.utils.listeners.OnTextViewDrawableTouchListener
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

/* Utils */
fun <T> Observable<T>.filterRapidClicks(windowDuration: Long = 1, timeUnit: TimeUnit = TimeUnit.SECONDS) = throttleFirst(windowDuration, timeUnit)
/* /Utils */

/**
 * Use .filterRapidClicks() to avoid accidental multiple clicks
 */
fun View.clicks(): Observable<View> {
	return Observable.create(OnClickObs(this))
}

/**
 * Use .filterRapidClicks() to avoid accidental multiple clicks
 */
fun View.drawableClicks(): Observable<OnTextViewDrawableTouchListener.TouchTarget> {
	return Observable.create(OnDrawableClickObs(this))
}

fun TextView.textChanges(): Observable<String> {
	return Observable.create(TextChangeObs(this))
}

fun DateTimeInput.textChanges(): Observable<DateTimeInputObs.Item> {
	return Observable.create(DateTimeInputObs(this))
}

fun IntInput.textChanges(): Observable<Long> {
	return Observable.create(LongChangeObs(this))
}

fun DoubleInput.textChanges(): Observable<Double> {
	return Observable.create(DoubleChangeObs(this))
}

fun CheckBox.checkedChanges(): Observable<Boolean> {
	return Observable.create(CheckBoxObs(this))
}