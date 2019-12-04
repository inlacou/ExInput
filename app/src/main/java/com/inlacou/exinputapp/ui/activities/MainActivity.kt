package com.inlacou.exinputapp.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inlacou.exinput.spinner.MaterialSpinner
import com.inlacou.exinput.exceptions.InvalidException
import com.inlacou.exinput.free.datetime.DateInput
import com.inlacou.exinput.free.datetime.DateTimeInput
import com.inlacou.exinput.free.datetime.TimeInput
import com.inlacou.exinput.free.numeric.vdouble.DoubleInput
import com.inlacou.exinput.free.numeric.vint.IntInput
import com.inlacou.exinput.free.text.TextInput
import com.inlacou.exinput.free.text.email.EmailInput
import com.inlacou.exinput.free.text.password.PasswordInput
import com.inlacou.exinput.free.text.phone.PhoneInput
import com.inlacou.exinput.free.text.search.SearchInput
import com.inlacou.exinput.free.trigger.TriggerInput
import com.inlacou.exinput.rx.drawableClicks
import com.inlacou.exinput.rx.filterRapidClicks
import com.inlacou.exinput.rx.textChanges
import com.inlacou.exinput.utils.listeners.OnTextViewDrawableTouchListener
import com.inlacou.exinput.utils.listeners.OnTextViewDrawableTouchListener.TouchTarget.*
import com.inlacou.exinputapp.R
import com.inlacou.exinputapp.adapters.ColorArrayAdapter
import com.inlacou.exinputapp.ui.views.ColorSimpleViewMdl
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

	var text: TextInput? = null
	var textRange: TextInput? = null
	var date: DateInput? = null
	var time: TimeInput? = null
	var dateTime: DateTimeInput? = null
	var inti: IntInput? = null
	var doublei: DoubleInput? = null
	var search: SearchInput? = null
	var password: PasswordInput? = null
	var email: EmailInput? = null
	var phone: PhoneInput? = null
	var etOnlyEditableThroughDrawables: PhoneInput? = null
	var etTrigger: TriggerInput? = null
	var spinner: MaterialSpinner? = null

	var button: Button? = null
	var buttonThrow: Button? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		Timber.plant(Timber.DebugTree())

		text = findViewById(R.id.text)
		textRange = findViewById(R.id.text_in_range)
		date = findViewById(R.id.date)
		time = findViewById(R.id.time)
		dateTime = findViewById(R.id.datetime)
		inti = findViewById(R.id.int_i)
		doublei = findViewById(R.id.double_i)
		search = findViewById(R.id.search)
		password = findViewById(R.id.password)
		email = findViewById(R.id.email)
		phone = findViewById(R.id.phone)
		etOnlyEditableThroughDrawables = findViewById(R.id.et_only_editable_through_drawables)
		etTrigger = findViewById(R.id.et_trigger)
		spinner = findViewById(R.id.spinner)

		button = findViewById(R.id.validate)

		//spinner?.adapter = ArrayAdapter(this, R.layout.common_simple_list_item, listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "ñ", "o", "p", "q", "r", "s", "t", "u"))
		spinner?.adapter = ColorArrayAdapter(this, listOf(ColorSimpleViewMdl("blue", R.color.basic_blue), ColorSimpleViewMdl("green", R.color.basic_green)))

		button?.setOnClickListener {
			text?.isValid()
			textRange?.isValid()
			date?.isValid()
			time?.isValid()
			dateTime?.isValid()
			inti?.isValid()
			doublei?.isValid()
			search?.isValid()
			password?.isValid()
			email?.isValid()
			phone?.isValid()
		}

		buttonThrow = findViewById(R.id.validate_throw)

		buttonThrow?.setOnClickListener {
			try {
				text?.isValidThrowExceptions()
				textRange?.isValidThrowExceptions()
				date?.isValidThrowExceptions()
				time?.isValidThrowExceptions()
				dateTime?.isValidThrowExceptions()
				inti?.isValidThrowExceptions()
				doublei?.isValidThrowExceptions()
				search?.isValidThrowExceptions()
				password?.isValidThrowExceptions()
				email?.isValidThrowExceptions()
				phone?.isValidThrowExceptions()
			}catch (ie: InvalidException){
				Toast.makeText(this, ie.message, Toast.LENGTH_LONG).show()
			}
		}

		text?.textChanges()?.debounce(200, TimeUnit.MILLISECONDS)?.observeOn(AndroidSchedulers.mainThread())?.subscribe {
			Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
		}
		etOnlyEditableThroughDrawables?.setOnTouchListener(object : OnTextViewDrawableTouchListener(interceptAllClick = true){
			override fun onDrawableClick(touchTarget: TouchTarget) {
				Toast.makeText(this@MainActivity, when(touchTarget){
					RIGHT -> { etOnlyEditableThroughDrawables?.text = "add"; "add" }
					LEFT -> { etOnlyEditableThroughDrawables?.text = "substract"; "substract" }
				}, Toast.LENGTH_SHORT).show()
			}
		})
		etOnlyEditableThroughDrawables?.drawableClicks()?.filterRapidClicks()?.observeOn(AndroidSchedulers.mainThread())?.subscribe {
			Toast.makeText(this@MainActivity, when(it){
				RIGHT -> { etOnlyEditableThroughDrawables?.text = "add"; "add" }
				LEFT -> { etOnlyEditableThroughDrawables?.text = "substract"; "substract" }
			}, Toast.LENGTH_SHORT).show()
		}
		dateTime?.textChanges()?.subscribe {

        }
		time?.textChanges()?.subscribe {

        }
		date?.textChanges()?.subscribe {

        }
		doublei?.textChanges()?.subscribe {

        }
		inti?.textChanges()?.subscribe {

        }
		etTrigger?.onWork = {
			Toast.makeText(this, "Put here your own action to change value!", Toast.LENGTH_LONG).show()
		}
	}
}
