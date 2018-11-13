package com.inlacou.exinput.rx

import android.view.View

import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

class OnClickObs constructor(private val view: View) : ObservableOnSubscribe<View> {
	@Throws(Exception::class)
	override fun subscribe(subscriber: ObservableEmitter<View>) {

		view.setOnClickListener { subscriber.onNext(view) }

		subscriber.setCancellable { view.setOnClickListener(null) }
	}
}