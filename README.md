# ExInput

[![](https://jitpack.io/v/inlacou/ExInput.svg)](https://jitpack.io/#inlacou/ExInput)

Better inputs for Android

# Input types:

1. [Text](#text-input)
2. [Text-email](#text-email-input)
3. [Text-password](#text-password-input)
4. [Text-phone](#text-phone-input)
5. [Text-search](#text-search-input)
6. [Number-int](#number-int-input)
7. [Number-double](#number-double-input)
8. [Date](#date-input)
9. [Time](#time-input)
10. [DateTime](#datetime-input)
11. [Icons](#icons)
11. [Styling](#styling)

## Text input
Free text input.
### Attributes
required: boolean

maxLength: integer

minLength: integer
### Example xml
```xml
<com.google.android.material.textfield.TextInputLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="text"
	android:theme="@style/exinput_textInputLayout"
	style="@style/exinput_textInputLayout">
	<com.inlacou.exinput.free.text.TextInput
		android:id="@+id/text"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:theme="@style/exinput_input"
		style="@style/exinput_input"
		app:required="true"/>
</com.google.android.material.textfield.TextInputLayout>
```
## Text email input
Email text input. valid() method also checks if input text is vaild email format.
### Attributes
required: boolean

maxLength: integer

minLength: integer
### Example xml
```xml
<com.google.android.material.textfield.TextInputLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="text"
	android:theme="@style/exinput_textInputLayout"
	style="@style/exinput_textInputLayout">
	<com.inlacou.exinput.free.text.email.EmailInput
		android:id="@+id/email"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:theme="@style/exinput_input"
		android:hint="email"/>
</com.google.android.material.textfield.TextInputLayout>
```
## Text password input
Password text input. Packs a button to show/hide password, which be disabled if needed through attributes.
### Attributes
required: boolean

maxLength: integer

minLength: integer

showHideButton: boolean
### Example xml
```xml
<com.google.android.material.textfield.TextInputLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="text"
	android:theme="@style/exinput_textInputLayout"
	style="@style/exinput_textInputLayout">
	<com.inlacou.exinput.free.text.password.PasswordInput
		android:id="@+id/password"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:theme="@style/exinput_input"
		android:hint="password"/>
</com.google.android.material.textfield.TextInputLayout>
```
## Text phone input
Phone text input. valid() method also checks if input text is vaild phone format.
### Attributes
required: boolean

maxLength: integer

minLength: integer
### Example xml
```xml
<com.google.android.material.textfield.TextInputLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="text"
	android:theme="@style/exinput_textInputLayout"
	style="@style/exinput_textInputLayout">
	<com.inlacou.exinput.free.text.phone.PhoneInput
		android:id="@+id/phone"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:theme="@style/exinput_input"
		android:hint="phone"/>
</com.google.android.material.textfield.TextInputLayout>
```
## Text search input
Search text input. Packs usual search and clear icons.
### Attributes
required: boolean

maxLength: integer

minLength: integer
### Example xml
```xml
<com.google.android.material.textfield.TextInputLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="text"
	android:theme="@style/exinput_textInputLayout"
	style="@style/exinput_textInputLayout">
	<com.inlacou.exinput.free.text.search.SearchInput
		android:id="@+id/search"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:theme="@style/exinput_input"
		android:drawablePadding="@dimen/general_all"
		android:padding="@dimen/general_all"
		android:hint="search"/>
</com.google.android.material.textfield.TextInputLayout>
```
## Number int input
Non floating number input.
### Attributes
maxDigits: integer

markThousands: boolean

thousandSeparator: string

required: boolean
### Example xml
```xml
<com.google.android.material.textfield.TextInputLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="text"
	android:theme="@style/exinput_textInputLayout"
	style="@style/exinput_textInputLayout">
	<com.inlacou.exinput.free.numeric.vint.IntInput
		android:id="@+id/int_i"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:theme="@style/exinput_input"
		app:maxDigits="5"
		android:hint="int"/>
</com.google.android.material.textfield.TextInputLayout>
```
## Number double input
Floating number input.
### Attributes
maxDigits: integer

maxIntegers: integer

maxDecimals: integer

markThousands: boolean

thousandSeparator: string

decimalSeparator: string

required: boolean
### Example xml
```xml
<com.google.android.material.textfield.TextInputLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="text"
	android:theme="@style/exinput_textInputLayout"
	style="@style/exinput_textInputLayout">
	<com.inlacou.exinput.free.numeric.vdouble.DoubleInput
		android:id="@+id/double_i"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:theme="@style/exinput_input"
		app:maxIntegers="3"
		app:maxDecimals="2"
		app:decimalSeparator=","
		app:markThousands="true"
		app:thousandSeparator="."
		android:hint="double"/>
</com.google.android.material.textfield.TextInputLayout>
```
## Date input
Date input.
### Attributes
required: boolean
### Example xml
```xml
<com.google.android.material.textfield.TextInputLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="text"
	android:theme="@style/exinput_textInputLayout"
	style="@style/exinput_textInputLayout">
	<com.inlacou.exinput.free.datetime.DateInput
		android:id="@+id/date"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:theme="@style/exinput_input"
		android:hint="date"/>
</com.google.android.material.textfield.TextInputLayout>
```
## Time input
Time input.
### Attributes
required: boolean
### Example xml
```xml
<com.google.android.material.textfield.TextInputLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="text"
	android:theme="@style/exinput_textInputLayout"
	style="@style/exinput_textInputLayout">
	<com.inlacou.exinput.free.datetime.TimeInput
		android:id="@+id/time"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:theme="@style/exinput_input"
		app:mode24h="false"
		android:hint="time"/>
</com.google.android.material.textfield.TextInputLayout>
```
## DateTime input
Date time input.
### Attributes
required: boolean
### Example xml
```xml
<com.google.android.material.textfield.TextInputLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="text"
	android:theme="@style/exinput_textInputLayout"
	style="@style/exinput_textInputLayout">
	<com.inlacou.exinput.free.datetime.DateTimeInput
		android:id="@+id/time"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:theme="@style/exinput_input"
		app:mode24h="true"
		android:hint="time"/>
</com.google.android.material.textfield.TextInputLayout>
```
## Icons
Add them as usual.
### Listener
Basic way:
```kt
yourTextInput?.setOnTouchListener(object : OnTextViewDrawableTouchListener(interceptAllClick = true){
	override fun onDrawableClick(touchTarget: TouchTarget) {
		when(it){
			RIGHT -> { /*Clicked on right/end drawable*/ }
			LEFT -> { /*Clicked on left/start drawable*/ }
			}
		}
	})
```
Rx way:
```kt
yourTextInput.drawableClicks()?.filterRapidClicks()?.observeOn(AndroidSchedulers.mainThread())?.subscribe {
	when(it){
		RIGHT -> { /*Clicked on right/end drawable*/ }
		LEFT -> { /*Clicked on left/start drawable*/ }
	}
}
```
### Example xml
```xml
android:drawableStart="@android:drawable/ic_delete"
android:drawableLeft="@android:drawable/ic_delete"
```
## Styling
Easiest way is to just override this colors:
```xml
<color name="exinput_focused_general">@color/colorAccent</color>
<color name="exinput_unfocused_general">@color/colorPrimary</color>
```
Or you can override this ones:
```xml
<color name="exinput_text_color">@color/exinput_basic_black</color>
<color name="exinput_focused_hint">@color/exinput_focused_general</color>
<color name="exinput_unfocused_hint">@color/exinput_unfocused_general</color>
<color name="exinput_focused_outline">@color/exinput_focused_general</color>
<color name="exinput_unfocused_outline">@color/exinput_unfocused_general</color>
<color name="exinput_focused_bottom_bar">@color/exinput_focused_general</color>
<color name="exinput_unfocused_bottom_bar">@color/exinput_unfocused_general</color>
```
Or you can extend this styles:
```xml

```
I you want full control you can make your own styles and ignore mine!
```xml
<style name="exinput_input"/> <!-- for text inputs -->
<style name="exinput_textInputLayout"/> <!-- For old layout -->
<style name="exinput_outlined_textInputLayout"/> <!-- For box layout -->
```
