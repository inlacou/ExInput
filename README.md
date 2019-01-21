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

Example xml:
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
## Text password input
## Text phone input
## Text search input
## Number int input
## Number double input
## Date input
## Time input
## DateTime input
## Icons
## Styling
