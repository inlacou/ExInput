package com.inlacou.exinput.rx.input

import android.text.Editable
import android.text.TextWatcher
import com.inlacou.exinput.free.datetime.DateInput
import com.inlacou.exinput.free.datetime.DateTimeInput

import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.util.*

class DateTimeInputObs constructor(private val view: DateTimeInput) : ObservableOnSubscribe<DateTimeInputObs.Item> {

	private lateinit var listener: TextWatcher

	@Throws(Exception::class)
	override fun subscribe(subscriber: ObservableEmitter<Item>) {
		listener = object: TextWatcher{
			override fun afterTextChanged(s: Editable?) {
				subscriber.onNext(Item(view.value))
			}

			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			}
		}

		view.addTextChangedListener(listener)

		subscriber.setCancellable { view.removeTextChangedListener(listener) }
	}

	data class Item(val calendar: Calendar?)

}