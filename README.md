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
## Number double input
## Date input
## Time input
## DateTime input
## Icons
## Styling
