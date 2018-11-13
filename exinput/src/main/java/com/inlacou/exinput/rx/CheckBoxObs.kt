package com.inlacou.exinput.rx

import android.widget.CheckBox

import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

class CheckBoxObs constructor(private val checkBox: CheckBox) : ObservableOnSubscribe<Boolean> {
	@Throws(Exception::class)
	override fun subscribe(subscriber: ObservableEmitter<Boolean>) {
		checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
			subscriber.onNext(isChecked)
		}

		subscriber.setCancellable { checkBox.setOnCheckedChangeListener(null) }
	}
}