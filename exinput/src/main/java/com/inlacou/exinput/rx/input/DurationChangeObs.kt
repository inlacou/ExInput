package com.inlacou.exinput.rx.input

import com.inlacou.exinput.free.spinner.ComplexSpinnerInput
import com.inlacou.exinput.free.spinner.dialog.duration.DialogDurationInput
import com.inlacou.inkkotlincommons.monads.Maybe

import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe

class DurationChangeObs constructor(private val view: DialogDurationInput) : ObservableOnSubscribe<Maybe<DialogDurationInput.Duration>> {


	@Throws(Exception::class)
	override fun subscribe(subscriber: ObservableEmitter<Maybe<DialogDurationInput.Duration>>) {
		view.onItemSelectedCallback = { view: ComplexSpinnerInput<DialogDurationInput.Duration>, duration: DialogDurationInput.Duration? ->
			subscriber.onNext(Maybe(duration))
		}

		view.onItemSelectedCallback = null
	}
}