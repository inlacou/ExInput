package com.inlacou.exinput.utils.extensions

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.widget.EditText
import android.widget.TextView

fun TextView.setTextColorResId(colorResId: Int){
	this.setTextColor(ColorStateList.valueOf(this.context.getColorCompat(colorResId)))
}

fun TextView.stripUnderlines() {
	class URLSpanNoUnderline(url: String) : URLSpan(url) {
		override fun updateDrawState(ds: TextPaint) {
			super.updateDrawState(ds)
			ds.isUnderlineText = false
		}
	}

	val s = SpannableString(text)
	val spans = s.getSpans(0, s.length, URLSpan::class.java)
	for (span in spans) {
		val start = s.getSpanStart(span)
		val end = s.getSpanEnd(span)
		s.removeSpan(span)
		s.setSpan(URLSpanNoUnderline(span.url), start, end, 0)
	}
	text = s
}

fun TextView.allowHTML(){
	movementMethod = LinkMovementMethod.getInstance()
}

fun TextView.setHTML(text: String){
	this.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
		Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
	}else{
		Html.fromHtml(text)
	}
}

fun EditText.setSelectionEnd(){
	setSelection(text.length)
}

/**
 * If not resId given, mantain the previous
 */
fun TextView.setDrawable(padding: Int, leftResId: Int? = null, topResId: Int? = null, rightResId: Int? = null, botResId: Int? = null){
	setCompoundDrawablesWithIntrinsicBounds(when{
		leftResId==null -> compoundDrawables[0]
		leftResId>=0 -> context.getDrawableCompat(leftResId)
		else -> null
	}, when{
		topResId==null -> compoundDrawables[1]
		topResId>=0 -> context.getDrawableCompat(topResId)
		else -> null
	}, when{
		rightResId==null -> compoundDrawables[2]
		rightResId>=0 -> context.getDrawableCompat(rightResId)
		else -> null
	}, when{
		botResId==null -> compoundDrawables[3]
		botResId>=0 -> context.getDrawableCompat(botResId)
		else -> null
	})
	compoundDrawablePadding = padding
}

fun TextView.setDrawableLeft(drawable: Drawable?, padding: Int? = null){
	setCompoundDrawablesWithIntrinsicBounds(drawable, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3])
	padding?.let { compoundDrawablePadding = padding }
}

fun TextView.setDrawableRight(drawable: Drawable?, padding: Int? = null){
	setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], compoundDrawables[1], drawable, compoundDrawables[3])
	padding?.let { compoundDrawablePadding = padding }
}

//Get out of here

/**
 * Shares the PNG image from Uri.
 * @param uri Uri of image to share.
 */
private fun shareImageUri(context: Context, uri: Uri) {
	val intent = Intent(Intent.ACTION_SEND)
	intent.putExtra(Intent.EXTRA_STREAM, uri)
	intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
	intent.type = "image/png"
	context.startActivity(intent)
}
