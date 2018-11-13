package com.inlacou.exinput.utils.listeners

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import com.inlacou.exinput.BaseInput

abstract class OnTextViewDrawableTouchListener : OnTouchListener {

	enum class TouchTarget {
		RIGHT, LEFT
	}

	override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
		var handled = false
		val textView = (view as TextView)
		if (motionEvent.action == MotionEvent.ACTION_UP) {
			val touchEventX = motionEvent.x
			textView.compoundDrawables[BaseInput.DRAWABLE_RIGHT]?.let {
				val touchAreaRight = view.right
				val touchAreaLeft = touchAreaRight - it.bounds.width()
				if (touchEventX >= touchAreaLeft && touchEventX <= touchAreaRight) {
					onDrawableClick(TouchTarget.RIGHT)
					handled = true
				}
			}
			textView.compoundDrawables[BaseInput.DRAWABLE_LEFT]?.let {
				val touchAreaLeft = view.left
				val touchAreaRight = touchAreaLeft + it.bounds.width()
				if (touchEventX >= touchAreaLeft && touchEventX <= touchAreaRight) {
					onDrawableClick(TouchTarget.LEFT)
					handled = true
				}
			}
		}
		return handled
	}

	abstract fun onDrawableClick(touchTarget: TouchTarget)
}