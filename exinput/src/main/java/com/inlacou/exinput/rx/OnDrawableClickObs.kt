package com.inlacou.exinput.rx

import android.view.View
import com.inlacou.exinput.utils.listeners.OnTextViewDrawableTouchListener
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe

class OnDrawableClickObs constructor(private val view: View) : ObservableOnSubscribe<OnTextViewDrawableTouchListener.TouchTarget> {
	@Throws(Exception::class)
	override fun subscribe(subscriber: ObservableEmitter<OnTextViewDrawableTouchListener.TouchTarget>) {

		view.setOnTouchListener(object : OnTextViewDrawableTouchListener(interceptAllClick = true){
			override fun onDrawableClick(touchTarget: TouchTarget) {
				subscriber.onNext(touchTarget)
			}
		})

		subscriber.setCancellable { view.setOnClickListener(null) }
	}
}