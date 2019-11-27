package com.inlacou.exinput.rx.input

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.inlacou.exinput.free.numeric.vdouble.DoubleInput

import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

class DoubleChangeObs constructor(private val view: DoubleInput) : ObservableOnSubscribe<Double> {

	private lateinit var listener: TextWatcher

	@Throws(Exception::class)
	override fun subscribe(subscriber: ObservableEmitter<Double>) {
		listener = object: TextWatcher{
			override fun afterTextChanged(s: Editable?) {
				subscriber.onNext(s?.toString()
					?.replace(view.thousandSeparator, "")
					?.replace(view.decimalSeparator, ".")
					?.toDoubleOrNull() ?: 0.0)
			}

			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
		}

		view.addTextChangedListener(listener)

		subscriber.setCancellable { view.removeTextChangedListener(listener) }
	}
}